package com.fullrandom.model

import java.time.LocalDate
import java.time.LocalTime

data class HourRange(
    val start: LocalTime,
    val end: LocalTime,
)