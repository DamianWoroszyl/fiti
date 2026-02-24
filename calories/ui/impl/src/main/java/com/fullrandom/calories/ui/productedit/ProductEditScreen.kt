package com.fullrandom.calories.ui.productedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fullrandom.fiti.calories.ui.impl.R
import com.fullrandom.fiti.core.ui.components.FitiTopBar

@Composable
fun ProductEditScreen(
    viewModel: ProductEditViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ProductEditScreenContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onKcalChange = viewModel::onKcalChange,
        onCarbsChange = viewModel::onCarbsChange,
        onProteinChange = viewModel::onProteinChange,
        onFatChange = viewModel::onFatChange,
        onSave = viewModel::onSave,
        onDelete = viewModel::onDelete,
        onBack = viewModel::onBack,
    )
}

@Composable
fun ProductEditScreenContent(
    uiState: ProductEditUiState,
    onNameChange: (String) -> Unit,
    onKcalChange: (String) -> Unit,
    onCarbsChange: (String) -> Unit,
    onProteinChange: (String) -> Unit,
    onFatChange: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        FitiTopBar(
            title = uiState.productName.ifBlank { stringResource(R.string.product_edit_title_new_product) },
            leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
            leftIconContentDescription = stringResource(R.string.product_edit_cd_back),
            onLeftIconClick = onBack,
            rightIcon = if (uiState.isEditMode) Icons.Default.Delete else null,
            rightIconContentDescription = if (uiState.isEditMode) {
                stringResource(R.string.product_edit_cd_delete)
            } else {
                null
            },
            onRightIconClick = if (uiState.isEditMode) onDelete else null,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            OutlinedTextField(
                value = uiState.productName,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.product_edit_field_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            MacroInputRow(
                label = stringResource(R.string.product_edit_label_kcal),
                value = uiState.kcalPer100g,
                onValueChange = onKcalChange,
            )
            Spacer(modifier = Modifier.height(8.dp))
            MacroInputRow(
                label = stringResource(R.string.product_edit_label_carbs),
                value = uiState.carbsPer100g,
                onValueChange = onCarbsChange,
            )
            Spacer(modifier = Modifier.height(8.dp))
            MacroInputRow(
                label = stringResource(R.string.product_edit_label_protein),
                value = uiState.proteinPer100g,
                onValueChange = onProteinChange,
            )
            Spacer(modifier = Modifier.height(8.dp))
            MacroInputRow(
                label = stringResource(R.string.product_edit_label_fat),
                value = uiState.fatPer100g,
                onValueChange = onFatChange,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.product_edit_button_save))
            }
        }
    }
}

@Composable
private fun MacroInputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true, name = "Create mode")
@Composable
private fun ProductEditScreenCreatePreview() {
    MaterialTheme {
        ProductEditScreenContent(
            uiState = ProductEditUiState(),
            onNameChange = {},
            onKcalChange = {},
            onCarbsChange = {},
            onProteinChange = {},
            onFatChange = {},
            onSave = {},
            onDelete = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Edit mode")
@Composable
private fun ProductEditScreenEditPreview() {
    MaterialTheme {
        ProductEditScreenContent(
            uiState = ProductEditUiState(
                productName = "Chicken breast",
                kcalPer100g = "165",
                carbsPer100g = "0",
                proteinPer100g = "31",
                fatPer100g = "3.6",
                isEditMode = true,
            ),
            onNameChange = {},
            onKcalChange = {},
            onCarbsChange = {},
            onProteinChange = {},
            onFatChange = {},
            onSave = {},
            onDelete = {},
            onBack = {},
        )
    }
}
