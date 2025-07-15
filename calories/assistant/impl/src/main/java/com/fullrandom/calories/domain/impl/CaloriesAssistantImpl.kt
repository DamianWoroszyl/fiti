package com.fullrandom.calories.domain.impl

import com.fullrandom.calories.domain.api.CaloriesAssistant
import com.fullrandom.model.HourRange

class CaloriesAssistantImpl : CaloriesAssistant {

    override fun saveCallHours(range: HourRange?) {
        // todo - should assistant storage be in separate module? Or maybe just use DataStore directly?
    }

    override fun getCallHours(): HourRange? {
        // todo
        return null
    }

    override fun init() {
        // todo
    }
}