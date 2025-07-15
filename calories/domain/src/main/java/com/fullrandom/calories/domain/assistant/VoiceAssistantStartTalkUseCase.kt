package com.fullrandom.calories.domain.assistant

import com.fullrandom.calories.domain.api.CaloriesAssistant
import javax.inject.Inject

class VoiceAssistantStartTalkUseCase @Inject constructor(
    private val caloriesAssistant: CaloriesAssistant
) {
    operator fun invoke() = caloriesAssistant.startTalk()
}