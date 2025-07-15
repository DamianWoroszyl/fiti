package com.fullrandom.calories.domain.api

interface CaloriesAssistant {
    fun startTalk()
    fun saveConfig(config: CaloriesAssistantConfig?)
    suspend fun getConfig(): CaloriesAssistantConfig?
    fun init()
}