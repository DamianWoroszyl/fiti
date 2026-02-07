package com.fullrandom.calories.ui.productedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductEditScreen(
    viewModel: ProductEditViewModel = viewModel()
) {

    Box(modifier = Modifier
        .background(Color.Red)
        .fillMaxSize())

}