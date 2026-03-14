package com.fullrandom.calories.localassets.impl

import android.content.Context
import com.fullrandom.calories.localassets.api.AssetLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class AssetLoaderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AssetLoader {

    override fun readLines(fileName: String): List<String> {
        return context.assets.open(fileName).bufferedReader().readLines()
    }
}
