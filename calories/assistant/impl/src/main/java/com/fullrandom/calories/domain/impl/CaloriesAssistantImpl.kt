package com.fullrandom.calories.domain.impl

import com.fullrandom.calories.domain.api.CaloriesAssistant
import com.fullrandom.calories.domain.api.CaloriesAssistantConfig

class CaloriesAssistantImpl : CaloriesAssistant {

    override fun startTalk() {
        // todo
    }

    override fun saveConfig(config: CaloriesAssistantConfig?) {
        // todo - should assistant storage be in separate module? Or maybe just use DataStore directly?
    }

    override suspend fun getConfig(): CaloriesAssistantConfig? {
        // todo
        return null
    }

    override fun init() {
        // todo
    }
}