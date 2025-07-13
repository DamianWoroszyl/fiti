package com.fullrandom.calories.domain.api

import com.fullrandom.model.HourRange

interface CaloriesAssistant {
    fun saveCallHours(range: HourRange?)
    fun getCallHours(): HourRange?
    fun init()
}