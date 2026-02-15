package com.fullrandom.calories.assistant.api

sealed class SpeechEvent {
    data class PartialResult(val result: String) : SpeechEvent()
    data class FinalResult(val result: String) : SpeechEvent()
    data class Error(val error: Throwable) : SpeechEvent()
}