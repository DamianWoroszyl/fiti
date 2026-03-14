package com.fullrandom.calories.localassets.api

interface AssetLoader {
    fun readLines(fileName: String): List<String>
}
