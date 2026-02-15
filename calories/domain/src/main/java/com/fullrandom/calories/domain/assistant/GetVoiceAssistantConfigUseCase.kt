package com.fullrandom.calories.domain.assistant

import com.fullrandom.calories.assistant.api.CaloriesAssistant
import com.fullrandom.calories.assistant.api.CaloriesAssistantConfig
import javax.inject.Inject

class GetVoiceAssistantConfigUseCase @Inject constructor(
    private val caloriesAssistant: CaloriesAssistant
) {
    suspend operator fun invoke(): CaloriesAssistantConfig? =
        caloriesAssistant.getConfig()
}