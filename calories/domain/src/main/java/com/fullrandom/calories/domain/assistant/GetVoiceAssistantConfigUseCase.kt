package com.fullrandom.calories.domain.assistant

import com.fullrandom.calories.domain.api.CaloriesAssistant
import com.fullrandom.calories.domain.api.CaloriesAssistantConfig
import javax.inject.Inject

class GetVoiceAssistantConfigUseCase @Inject constructor(
    private val caloriesAssistant: CaloriesAssistant
) {
    suspend operator fun invoke(): CaloriesAssistantConfig? =
        caloriesAssistant.getConfig()
}