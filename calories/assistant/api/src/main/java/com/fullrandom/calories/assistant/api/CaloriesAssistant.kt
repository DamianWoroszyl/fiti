package com.fullrandom.calories.assistant.api

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface CaloriesAssistant {

    val listeningState: StateFlow<Boolean>
    val events: SharedFlow<SpeechEvent>

    fun saveConfig(config: CaloriesAssistantConfig?)
    suspend fun getConfig(): CaloriesAssistantConfig?

    fun startSpeechRecognition()
    fun stopSpeechRecognition()

    fun destroy()
}