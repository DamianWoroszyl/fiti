package com.fullrandom.calories.ui.productdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ProductDetailsScreenContent(
        uiState = uiState,
        onGramsChange = viewModel::onGramsChange,
        onAddToTarget = viewModel::onAddToTarget,
        onBack = viewModel::onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreenContent(
    uiState: ProductDetailsUiState,
    onGramsChange: (String) -> Unit,
    onAddToTarget: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.productName.ifBlank { "Product" },
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            if (uiState.targetName != null) {
                Text(
                    text = "Add to ${uiState.targetName}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = uiState.gramsInput,
                        onValueChange = onGramsChange,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.width(120.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "g",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onAddToTarget,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Add to ${uiState.targetName}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (uiState.targetName != null) {
                Text(
                    text = "For ${uiState.gramsInput.ifBlank { "0" }}g",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    MacroColumn(label = "kcal", value = uiState.kcalForGrams.formatMacro())
                    MacroColumn(label = "Carbs", value = uiState.carbsForGrams.formatMacro() + "g")
                    MacroColumn(label = "Protein", value = uiState.proteinForGrams.formatMacro() + "g")
                    MacroColumn(label = "Fat", value = uiState.fatForGrams.formatMacro() + "g")
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Per 100g",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                MacroColumn(label = "kcal", value = uiState.kcalPer100g.formatMacro())
                MacroColumn(label = "Carbs", value = uiState.carbsPer100g.formatMacro() + "g")
                MacroColumn(label = "Protein", value = uiState.proteinPer100g.formatMacro() + "g")
                MacroColumn(label = "Fat", value = uiState.fatPer100g.formatMacro() + "g")
            }
        }
    }
}

@Composable
private fun MacroColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

private fun Double.formatMacro(): String = if (this == this.toLong().toDouble()) {
    this.toLong().toString()
} else {
    "%.1f".format(this)
}

@Preview(showBackground = true, name = "View only")
@Composable
private fun ProductDetailsViewOnlyPreview() {
    MaterialTheme {
        ProductDetailsScreenContent(
            uiState = ProductDetailsUiState(
                productName = "Chicken breast",
                kcalPer100g = 165.0,
                carbsPer100g = 0.0,
                proteinPer100g = 31.0,
                fatPer100g = 3.6,
                targetName = null,
                gramsInput = "100",
            ),
            onGramsChange = {},
            onAddToTarget = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Add to meal")
@Composable
private fun ProductDetailsAddToMealPreview() {
    MaterialTheme {
        ProductDetailsScreenContent(
            uiState = ProductDetailsUiState(
                productName = "Chicken breast",
                kcalPer100g = 165.0,
                carbsPer100g = 0.0,
                proteinPer100g = 31.0,
                fatPer100g = 3.6,
                targetName = "Dinner",
                gramsInput = "150",
            ),
            onGramsChange = {},
            onAddToTarget = {},
            onBack = {},
        )
    }
}
