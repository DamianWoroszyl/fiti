package com.fullrandom.calories.ui

import android.Manifest
import android.content.pm.PackageManager
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fullrandom.model.ConsumedProduct
import com.fullrandom.model.Product
import java.time.LocalDate

private const val TAG = "CaloriesSummaryScreen"
@Composable
fun CaloriesSummaryScreen(
    viewModel: CaloriesSummaryViewModel = viewModel()
) {
    val finalRecognizedText by viewModel.finalRecognizedText.collectAsState()
    val partialSpeechDisplay by viewModel.partialSpeechDisplay.collectAsState()
    val lastError by viewModel.lastError.collectAsState() // For displaying errors

    // Example state for other parts of your UI, if used by CaloriesSummaryScreenContent
    val consumedProducts = remember { listOf<ConsumedProduct>() } // Placeholder

    var hasAudioPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Check initial permission status
    LaunchedEffect(Unit) {
        hasAudioPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasAudioPermission = isGranted
            if (!isGranted) {
            } else {
                // Permission granted, user might need to tap button again if they tapped before
                // Or you could trigger startListening if an intent was pending
                viewModel.clearLastSpeechError() // Clear permission error if it was set
            }
        }
    )

    val onToggleListeningClick = {
        if (!hasAudioPermission) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        } else {
            viewModel.toggleAssistantListening()
        }
    }

    CaloriesSummaryScreenContent(
        consumedProducts = consumedProducts,
        recognizedSpeechText = finalRecognizedText,
        partialSpeechText = partialSpeechDisplay,
        hasAudioPermission = hasAudioPermission,
        onAddConsumedProductClicked = { viewModel.onAddConsumedProductClicked() },
        onTalkWithAssistantClicked = { viewModel.onTalkWithAssistantClicked() },
        onToggleSpeechRecognition = onToggleListeningClick,
        onClearRecognizedText = { viewModel.clearRecognizedTextFromVm() },
        isListening = viewModel.listeningState
    )

    // You might want a Snackbar or a Text element to display viewModel.lastError
    if (lastError != null) {
        // Show snackbar or text with error: lastError
        // And an option to dismiss it, e.g., viewModel.clearLastSpeechError()
    }
}


fun getErrorText(errorCode: Int): String {
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
        else -> "Unknown speech error"
    }
}

/**
 * Stateless Composable that displays the UI based on the provided state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriesSummaryScreenContent(
    consumedProducts: List<ConsumedProduct>,
    recognizedSpeechText: String?,
    partialSpeechText: String?,
    isListening: Boolean,
    hasAudioPermission: Boolean,
    onAddConsumedProductClicked: () -> Unit,
    onTalkWithAssistantClicked: () -> Unit,
    onToggleSpeechRecognition: () -> Unit,
    onClearRecognizedText: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Calories Summary") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onAddConsumedProductClicked) { Text("Add Product") }
                Button(onClick = onTalkWithAssistantClicked) { Text("Assistant") }
                Button(
                    onClick = onToggleSpeechRecognition,
                ) {
                    Text(if (isListening) "Stop Listening" else if (hasAudioPermission) "Start Dictation" else "Grant Mic & Speak")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Display partial or final recognized text
            val currentSpeechDisplay = partialSpeechText ?: recognizedSpeechText
            if (!currentSpeechDisplay.isNullOrEmpty()) {
                Text(
                    text = if (isListening && !partialSpeechText.isNullOrEmpty()) "Heard: $partialSpeechText" else "You said: $recognizedSpeechText",
                    style = MaterialTheme.typography.bodyLarge
                )
                if (!recognizedSpeechText.isNullOrEmpty() && !isListening) { // Show clear only for final results
                    Button(
                        onClick = onClearRecognizedText,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Clear Speech")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }


            Spacer(modifier = Modifier.height(16.dp))
            // ... Rest of the UI (LazyColumn for consumedProducts) ...
            if (consumedProducts.isEmpty() && (recognizedSpeechText.isNullOrEmpty() && partialSpeechText.isNullOrEmpty())) {
                Text("No products consumed today.")
            } else if (consumedProducts.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(consumedProducts, key = { it.id }) { product ->
                        ConsumedProductItem(product)
                    }
                }
            }
        }
    }
}

@Composable
fun ConsumedProductItem(consumedProduct: ConsumedProduct) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = consumedProduct.productName,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Amount: ${consumedProduct.amountGrams}g")
            Text(text = "Kcal: ${consumedProduct.kcal.toInt()}")
            consumedProduct.product?.let {
                // Text(text = "Brand: ${it.brand ?: "N/A"}")
            }
        }
    }
}

@Preview(showBackground = true, name = "Content - Listening")
@Composable
fun CaloriesSummaryScreenContentListeningPreview() {
    MaterialTheme {
        CaloriesSummaryScreenContent(
            consumedProducts = emptyList(),
            recognizedSpeechText = null,
            partialSpeechText = "Listening for food...",
            isListening = true,
            hasAudioPermission = true,
            onAddConsumedProductClicked = {},
            onTalkWithAssistantClicked = {},
            onToggleSpeechRecognition = {},
            onClearRecognizedText = {}
        )
    }
}

@Preview(showBackground = true, name = "Content - Result")
@Composable
fun CaloriesSummaryScreenContentResultPreview() {
    val product1 = Product("p1", "Apple", 14.0, 0.2, 0.3, 52.0)
    val previewConsumedProducts = listOf(
        ConsumedProduct(
            "cp1",
            "mealA",
            0,
            LocalDate.now(),
            product1,
            150.0,
            product1.name,
            product1.carbohydrates * 1.5,
            product1.fat * 1.5,
            product1.protein * 1.5,
            product1.kcal * 1.5
        ),
    )
    MaterialTheme {
        CaloriesSummaryScreenContent(
            consumedProducts = previewConsumedProducts,
            recognizedSpeechText = "One apple and two bananas",
            partialSpeechText = null,
            isListening = false,
            hasAudioPermission = true,
            onAddConsumedProductClicked = {},
            onTalkWithAssistantClicked = {},
            onToggleSpeechRecognition = {},
            onClearRecognizedText = {}
        )
    }
}