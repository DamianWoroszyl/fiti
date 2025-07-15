package com.fullrandom.calories.domain.assistant

import com.fullrandom.calories.domain.api.CaloriesAssistant
import com.fullrandom.calories.domain.api.CaloriesAssistantConfig
import javax.inject.Inject

class SaveVoiceAssistantConfigUseCase @Inject constructor(
    private val caloriesAssistant: CaloriesAssistant
) {
    operator fun invoke(config: CaloriesAssistantConfig?) =
        caloriesAssistant.saveConfig(config)
}