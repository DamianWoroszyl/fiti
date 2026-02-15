package com.fullrandom.calories.domain.assistant

import com.fullrandom.calories.assistant.api.CaloriesAssistant
import com.fullrandom.calories.assistant.api.CaloriesAssistantConfig
import javax.inject.Inject

class SaveVoiceAssistantConfigUseCase @Inject constructor(
    private val caloriesAssistant: CaloriesAssistant
) {
    operator fun invoke(config: CaloriesAssistantConfig?) =
        caloriesAssistant.saveConfig(config)
}