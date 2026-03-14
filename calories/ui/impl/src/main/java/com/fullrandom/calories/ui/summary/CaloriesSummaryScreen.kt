package com.fullrandom.calories.ui.summary

import android.Manifest
import android.content.pm.PackageManager
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fullrandom.fiti.calories.ui.impl.R
import com.fullrandom.model.ConsumedMeal
import com.fullrandom.model.DayCalories
import com.fullrandom.model.Meal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val TAG = "CaloriesSummaryScreen"

@Composable
fun CaloriesSummaryScreen(
    viewModel: CaloriesSummaryViewModel = viewModel()
) {
    val finalRecognizedText by viewModel.finalRecognizedText.collectAsState()
    val partialSpeechDisplay by viewModel.partialSpeechDisplay.collectAsState()
    val lastError by viewModel.lastError.collectAsState()
    val weekCalories by viewModel.weekCalories.collectAsState()

    var hasAudioPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        hasAudioPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            hasAudioPermission = isGranted
            if (isGranted) {
                viewModel.clearLastSpeechError()
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
        weekCalories = weekCalories,
        recognizedSpeechText = finalRecognizedText,
        partialSpeechText = partialSpeechDisplay,
        hasAudioPermission = hasAudioPermission,
        isListening = viewModel.listeningState,
        onToggleSpeechRecognition = onToggleListeningClick,
        onClearRecognizedText = { viewModel.clearRecognizedTextFromVm() },
        onAddToMealClicked = { meal: Meal, date: LocalDate -> viewModel.onAddToMealClicked(meal, date) },
        onBrowseProductsClicked = { viewModel.onBrowseProductsClicked() },
        onLoadInitialProductsClicked = { viewModel.onLoadInitialProductsClicked() },
    )

    if (lastError != null) {
        // Show snackbar or text with error: lastError
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriesSummaryScreenContent(
    weekCalories: List<DayCalories>,
    recognizedSpeechText: String?,
    partialSpeechText: String?,
    isListening: Boolean,
    hasAudioPermission: Boolean,
    onToggleSpeechRecognition: () -> Unit,
    onClearRecognizedText: () -> Unit,
    onAddToMealClicked: (Meal, LocalDate) -> Unit,
    onBrowseProductsClicked: () -> Unit,
    onLoadInitialProductsClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.summary_screen_title)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = onToggleSpeechRecognition) {
                    Text(
                        if (isListening) {
                            stringResource(R.string.summary_button_stop_listening)
                        } else if (hasAudioPermission) {
                            stringResource(R.string.summary_button_start_dictation)
                        } else {
                            stringResource(R.string.summary_button_grant_mic)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            val currentSpeechDisplay: String? = partialSpeechText ?: recognizedSpeechText
            if (!currentSpeechDisplay.isNullOrEmpty()) {
                Text(
                    text = if (isListening && !partialSpeechText.isNullOrEmpty()) {
                        stringResource(R.string.summary_speech_heard, partialSpeechText)
                    } else {
                        stringResource(R.string.summary_speech_said, recognizedSpeechText.orEmpty())
                    },
                    style = MaterialTheme.typography.bodyLarge,
                )
                if (!recognizedSpeechText.isNullOrEmpty() && !isListening) {
                    Button(
                        onClick = onClearRecognizedText,
                        modifier = Modifier.padding(top = 4.dp),
                    ) {
                        Text(stringResource(R.string.summary_button_clear_speech))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            val pagerState = rememberPagerState(pageCount = { weekCalories.size })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) { pageIndex: Int ->
                val dayCalories: DayCalories = weekCalories[pageIndex]
                DayPage(
                    pageIndex = pageIndex,
                    dayCalories = dayCalories,
                    onAddToMealClicked = onAddToMealClicked,
                )
            }

            Button(
                onClick = onBrowseProductsClicked,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.summary_button_browse_products))
            }
            Button(
                onClick = onLoadInitialProductsClicked,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.summary_button_load_initial_products))
            }
        }
    }
}

@Composable
private fun DayPage(
    pageIndex: Int,
    dayCalories: DayCalories,
    onAddToMealClicked: (Meal, LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
    ) {
        val dayHeader: String = when (pageIndex) {
            0 -> stringResource(R.string.summary_day_today)
            1 -> stringResource(R.string.summary_day_tomorrow)
            else -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(dayCalories.date)
        }
        Text(
            text = dayHeader,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        if (dayCalories.consumedMeals.isEmpty()) {
            Text(
                text = stringResource(R.string.summary_no_meals),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(dayCalories.consumedMeals, key = { it.meal.id }) { consumedMeal: ConsumedMeal ->
                    MealCard(
                        consumedMeal = consumedMeal,
                        date = dayCalories.date,
                        onAddToMealClicked = onAddToMealClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun MealCard(
    consumedMeal: ConsumedMeal,
    date: LocalDate,
    onAddToMealClicked: (Meal, LocalDate) -> Unit,
) {
    val totalKcal: Double = consumedMeal.products.sumOf { it.kcalPer100g * it.amountGrams / 100.0 }
    val productCount: Int = consumedMeal.products.size

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onAddToMealClicked(consumedMeal.meal, date) }
                    .padding(16.dp),
            ) {
                Text(
                    text = consumedMeal.meal.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.summary_meal_kcal, totalKcal),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(R.string.summary_meal_products_count, productCount),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = { onAddToMealClicked(consumedMeal.meal, date) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.summary_meal_add_content_description),
                )
            }
        }
    }
}
