package com.fullrandom.calories.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandom.calories.assistant.api.CaloriesAssistant
import com.fullrandom.calories.assistant.api.SpeechEvent
import com.fullrandom.calories.domain.ObserveCaloriesUseCase
import com.fullrandom.calories.domain.SearchProductUseCase
import com.fullrandom.calories.domain.assistant.VoiceAssistantStartTalkUseCase
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys.ProductDetailsScreenNavKey.AddTarget
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import com.fullrandom.model.ConsumedMeal
import com.fullrandom.model.DateRange
import com.fullrandom.model.Meal
import com.fullrandom.model.Product
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel(assistedFactory = CaloriesSummaryViewModel.Factory::class)
class CaloriesSummaryViewModel @AssistedInject constructor(
    @Assisted private val navKey: CaloriesUiNavKeys.SummaryScreenNavKey,
    private val observeCaloriesUseCase: ObserveCaloriesUseCase,
    private val searchProductUseCase: SearchProductUseCase,
    private val voiceAssistantCallNowUseCase: VoiceAssistantStartTalkUseCase,
    private val navigator: Navigator,
    private val caloriesAssistant: CaloriesAssistant,
) : ViewModel() {

    val weekDays: List<LocalDate> = (0L..6L).map { offset: Long -> LocalDate.now().plusDays(offset) }

    private val _mealsPerDay = MutableStateFlow<Map<LocalDate, List<ConsumedMeal>>>(
        weekDays.associateWith { emptyList() }
    )
    val mealsPerDay: StateFlow<Map<LocalDate, List<ConsumedMeal>>> = _mealsPerDay

    private val _finalRecognizedText = MutableStateFlow<String?>(null)
    val finalRecognizedText: StateFlow<String?> = _finalRecognizedText

    private val _partialSpeechDisplay = MutableStateFlow<String?>(null)
    val partialSpeechDisplay: StateFlow<String?> = _partialSpeechDisplay

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError

    val listeningState: Boolean
        get() = caloriesAssistant.listeningState.value

    init {
        loadWeekCalories()
        observeSpeechRecognitionResults()
    }

    private fun loadWeekCalories() {
        viewModelScope.launch {
            val range = DateRange(weekDays.first(), weekDays.last())
            observeCaloriesUseCase(range).collect { consumedMeals: List<ConsumedMeal> ->
                _mealsPerDay.value = weekDays.associateWith { day: LocalDate ->
                    consumedMeals.filter { consumedMeal: ConsumedMeal -> consumedMeal.date == day }
                }
            }
        }
    }

    private fun observeSpeechRecognitionResults() {
        viewModelScope.launch {
            caloriesAssistant.events.collect { event ->
                when (event) {
                    is SpeechEvent.Error -> _finalRecognizedText.value = event.error.message
                    is SpeechEvent.FinalResult -> {
                        _finalRecognizedText.value = event.result
                        _partialSpeechDisplay.value = null
                    }
                    is SpeechEvent.PartialResult -> {
                        _partialSpeechDisplay.value = event.result
                    }
                }
            }
        }
    }

    fun onMealClicked(meal: Meal, date: LocalDate) {
        viewModelScope.launch {
            val product: Product = searchProductUseCase("").first().firstOrNull() ?: return@launch
            navigator.navigate(
                CaloriesUiNavKeys.ProductDetailsScreenNavKey(
                    productId = product.id,
                    addTarget = AddTarget.MealTarget(
                        mealId = meal.id,
                        date = date.toEpochDay(),
                    ),
                )
            )
        }
    }

    fun onAddConsumedProductClicked() {
//        navigator.navigateToAddConsumedProduct()
    }

    fun onTalkWithAssistantClicked() {
//        voiceAssistantCallNowUseCase()
        TODO("probably only dictation will be used")
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

    @AssistedFactory
    interface Factory {
        fun create(navKey: CaloriesUiNavKeys.SummaryScreenNavKey): CaloriesSummaryViewModel
    }
}
