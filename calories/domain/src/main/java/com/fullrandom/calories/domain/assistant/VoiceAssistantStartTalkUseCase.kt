package com.fullrandom.calories.domain.assistant

import com.fullrandom.calories.assistant.api.CaloriesAssistant
import javax.inject.Inject

class VoiceAssistantStartTalkUseCase @Inject constructor(
    private val caloriesAssistant: CaloriesAssistant
) {
    operator fun invoke() = caloriesAssistant.startSpeechRecognition()
}