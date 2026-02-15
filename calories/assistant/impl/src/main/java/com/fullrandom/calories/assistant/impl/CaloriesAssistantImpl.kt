package com.fullrandom.calories.assistant.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.fullrandom.calories.assistant.api.CaloriesAssistant
import com.fullrandom.calories.assistant.api.CaloriesAssistantConfig
import com.fullrandom.calories.assistant.api.SpeechEvent
import com.fullrandom.calories.assistant.api.exception.CaloriesAssistantException
import com.fullrandom.calories.assistant.api.exception.SpeechRecognitionException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CaloriesAssistantImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) : CaloriesAssistant {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Main // todo later inject

    private val coroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    private var speechRecognizer: SpeechRecognizer? = null

    private var loopJob: Job? = null
    private var accumulatedTextBuilder = StringBuilder()

    private val _listeningState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val listeningState: StateFlow<Boolean> = _listeningState
    private val _events: MutableSharedFlow<SpeechEvent> = MutableSharedFlow()
    override val events: SharedFlow<SpeechEvent> = _events

    private fun createRecognizerIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "pl-PL"
            ) // todo fix language to be default to device language
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, applicationContext.packageName)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 5000L)
            putExtra(
                RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
                5000L
            )
        }
    }

    override fun startSpeechRecognition() {
        if (listeningState.value) {
            return
        }

        if (!SpeechRecognizer.isRecognitionAvailable(applicationContext)) {
            coroutineScope.launch {
                SpeechEvent.Error(CaloriesAssistantException("Speech recognition not available"))
            }
            return
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
        speechRecognizer?.setRecognitionListener(recognitionListener)

        _listeningState.value = true
        accumulatedTextBuilder.clear()

        loopJob?.cancel()
        loopJob = coroutineScope.launch {
            try {
                speechRecognizer!!.startListening(createRecognizerIntent())
                delay(MAX_RECOGNITION_DURATION_MS)
                if (_listeningState.value) {
                    Log.d(TAG, "Max recognition time reached for loop.")
                    stopListeningInternal(stopReason = "Timeout")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception during loop start or delay: ${e.message}")
                stopListeningInternal(stopReason = "Error: ${e.message}", error = e)
                // todo rethrow if its cancellation
            }
        }
    }

    override fun stopSpeechRecognition() {
        if (!listeningState.value && loopJob == null) {
            return
        }

        stopListeningInternal(stopReason = "User action")
    }

    private fun stopListeningInternal(
        stopReason: String,
        error: Throwable? = null
    ) {
        if (!listeningState.value && loopJob == null) {
            Log.d(
                TAG,
                "Internal stop called but loop appears already stopped. Reason: $stopReason"
            )
            return
        }

        Log.d(TAG, "Stop listening internal, reason: $stopReason")

        error?.let {
            coroutineScope.launch {
                _events.emit(SpeechEvent.Error(error))
            }
        }

        _listeningState.value = false
        loopJob?.cancel()
        loopJob = null
        coroutineScope.launch {
            speechRecognizer?.stopListening()
        }

        val finalFullText = accumulatedTextBuilder.toString()
        coroutineScope.launch {
            _events.emit(SpeechEvent.FinalResult(finalFullText))
        }
        accumulatedTextBuilder.clear()
    }

    override fun saveConfig(config: CaloriesAssistantConfig?) {
        /* TODO */
    }

    override suspend fun getConfig(): CaloriesAssistantConfig? {
        /* TODO */ return null
    }


    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            Log.d(TAG, "RecognitionListener: onReadyForSpeech")
        }

        override fun onBeginningOfSpeech() {
            Log.d(TAG, "RecognitionListener: onBeginningOfSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {
            Log.d(TAG, "RecognitionListener: onEndOfSpeech")
        }

        override fun onError(error: Int) {
            val errorMessage = getErrorText(error)
            Log.e(TAG, "RL: onError: $errorMessage (code: $error)")

            if (!_listeningState.value) return

            val shouldStopLoop = when (error) {
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS,
                SpeechRecognizer.ERROR_CLIENT,
                SpeechRecognizer.ERROR_SERVER,
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY,
                SpeechRecognizer.ERROR_NETWORK,
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> true

                else -> false // For NO_MATCH, SPEECH_TIMEOUT, AUDIO etc., try to continue loop
            }

            if (shouldStopLoop) {
                stopListeningInternal(
                    stopReason = errorMessage,
                    error = SpeechRecognitionException(error.toString())
                )
            } else if (SpeechRecognizer.isRecognitionAvailable(applicationContext)) {
                Log.d(TAG, "RecognitionListener: Retrying after error: $errorMessage")
                try {
                    speechRecognizer?.startListening(createRecognizerIntent())
                } catch (e: Exception) {
                    Log.e(TAG, "RecognitionListener: Failed to restart after error", e)
                    stopListeningInternal(
                        stopReason = "Restart failed: ${e.message}",
                        error = SpeechRecognitionException(e.message ?: "")
                    )
                }
            } else {
                stopListeningInternal(
                    stopReason = "Recognizer unavailable after error: $errorMessage",
                    error = SpeechRecognitionException("Recognizer unavailable after error: $errorMessage")
                )
            }
        }

        override fun onResults(results: Bundle?) {
            if (!listeningState.value) return

            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            val recognizedSegment = matches?.getOrNull(0)
            Log.d(TAG, "onResults segment: $recognizedSegment")

            if (!recognizedSegment.isNullOrEmpty()) {
                accumulatedTextBuilder.append(if (accumulatedTextBuilder.isNotEmpty()) " " else "")
                    .append(recognizedSegment)
                coroutineScope.launch {
                    _events.emit(SpeechEvent.PartialResult(accumulatedTextBuilder.toString()))
                }

                if (recognizedSegment.contains(STOP_PHRASE, ignoreCase = true)) {
                    Log.i(TAG, "Stop phrase detected.")
                    stopListeningInternal(stopReason = "Stop phrase detected")
                    return
                }
            }

            if (_listeningState.value && SpeechRecognizer.isRecognitionAvailable(applicationContext)) {
                try {
                    speechRecognizer?.startListening(createRecognizerIntent())
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to restart in onResults", e)
                    stopListeningInternal(
                        stopReason = "Restart failed: ${e.message}",
                        error = SpeechRecognitionException("Failed to restart loop")
                    )
                }
            } else if (_listeningState.value) {
                stopListeningInternal(stopReason = "Recognizer unavailable", error = SpeechRecognitionException("Failed to restart loop"))
            }
        }

        override fun onPartialResults(partialResults: Bundle?) {
            if (!_listeningState.value) return
            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            val partialText = matches?.getOrNull(0)
            if (!partialText.isNullOrEmpty()) {
                val previewText =
                    accumulatedTextBuilder.toString() + (if (accumulatedTextBuilder.isNotEmpty() && !partialText.startsWith(
                            " "
                        )
                    ) " " else "") + partialText
                coroutineScope.launch { _events.emit(SpeechEvent.PartialResult(previewText)) }
            }
        }

        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    // Helper similar to the one in Composable
    private fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No speech match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognizer busy"
            SpeechRecognizer.ERROR_SERVER -> "Server error"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Unknown speech error ($errorCode)"
        }
    }

    override fun destroy() {
        Log.d(TAG, "Destroying CaloriesAssistantImpl.")
        stopListeningInternal("Assistant destroyed")
        speechRecognizer?.destroy()
        speechRecognizer = null
        loopJob?.cancel()
    }

    companion object {
        private const val MAX_RECOGNITION_DURATION_MS = 5 * 60 * 1000L
        private const val STOP_PHRASE = "stop"
        private val TAG = CaloriesAssistantImpl::class.java.simpleName
    }
}