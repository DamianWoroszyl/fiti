package com.fullrandom.calories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandom.calories.assistant.api.CaloriesAssistant
import com.fullrandom.calories.assistant.api.SpeechEvent
import com.fullrandom.calories.domain.ObserveCaloriesUseCase
import com.fullrandom.calories.domain.assistant.VoiceAssistantStartTalkUseCase
import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.DateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CaloriesSummaryViewModel @Inject constructor(
    private val observeCaloriesUseCase: ObserveCaloriesUseCase,
    private val voiceAssistantCallNowUseCase: VoiceAssistantStartTalkUseCase,
    private val navigator: Navigator,
    private val caloriesAssistant: CaloriesAssistant,
) : ViewModel(){

    private val _consumedProducts = MutableStateFlow<List<ConsumedProduct>>(emptyList())
    val consumedProducts: StateFlow<List<ConsumedProduct>> = _consumedProducts

    private val _finalRecognizedText = MutableStateFlow<String?>(null)
    val finalRecognizedText: StateFlow<String?> = _finalRecognizedText

    private val _partialSpeechDisplay = MutableStateFlow<String?>(null)
    val partialSpeechDisplay: StateFlow<String?> = _partialSpeechDisplay

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError

    val listeningState: Boolean
        get() = caloriesAssistant.listeningState.value

    init {
        loadConsumedProducts()
        observeSpeechRecognitionResults()
    }

    private fun loadConsumedProducts() {
        viewModelScope.launch {
            val today = LocalDate.now()
            val dateRange = DateRange(today, today)
            observeCaloriesUseCase(dateRange).collect { dayCaloriesList ->
                _consumedProducts.value = dayCaloriesList.flatMap { it.consumedProducts }
            }
        }
    }

    private fun observeSpeechRecognitionResults() {
        viewModelScope.launch {
            caloriesAssistant.events.collect { event ->
                println("dwtest: " + event)
                when(event) {
                    is SpeechEvent.Error -> _finalRecognizedText.value = event.error.message
                    is SpeechEvent.FinalResult -> {
                        println("dwtest abc")
                        _finalRecognizedText.value = event.result
                        _partialSpeechDisplay.value = null
                    }
                    is SpeechEvent.PartialResult -> {
                        println("dwtest cde")
                        _partialSpeechDisplay.value = event.result
                    }
                }
            }
        }
    }

    fun onAddConsumedProductClicked() {
        navigator.navigateToAddConsumedProduct()
    }

    fun onTalkWithAssistantClicked() {
        voiceAssistantCallNowUseCase()
    }

    fun toggleAssistantListening() {
        if (caloriesAssistant.listeningState.value) {
            caloriesAssistant.stopSpeechRecognition()
        } else {
            _finalRecognizedText.value = null
            _lastError.value = null
            caloriesAssistant.startSpeechRecognition()
        }
    }

    fun clearLastSpeechError() {
        _lastError.value = null
    }
    fun clearRecognizedTextFromVm() {
        _finalRecognizedText.value = null
        _partialSpeechDisplay.value = null
    }

    override fun onCleared() {
        super.onCleared()
        caloriesAssistant.destroy()
    }
}
