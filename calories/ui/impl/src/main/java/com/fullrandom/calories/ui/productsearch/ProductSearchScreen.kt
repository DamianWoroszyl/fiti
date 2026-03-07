package com.fullrandom.calories.ui.productsearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fullrandom.fiti.calories.ui.impl.R
import com.fullrandom.fiti.core.ui.components.FitiTopBar
import com.fullrandom.model.Product

@Composable
fun ProductSearchScreen(
    viewModel: ProductSearchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ProductSearchScreenContent(
        screenTitle = viewModel.screenTitle,
        uiState = uiState,
        onQueryChanged = viewModel::onQueryChanged,
        onProductClicked = viewModel::onProductClicked,
        onAddProductClicked = viewModel::onAddProductClicked,
        onBack = viewModel::onBack,
    )
}

@Composable
fun ProductSearchScreenContent(
    screenTitle: String?,
    uiState: ProductSearchUiState,
    onQueryChanged: (String) -> Unit,
    onProductClicked: (Product) -> Unit,
    onAddProductClicked: () -> Unit,
    onBack: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        FitiTopBar(
            title = screenTitle ?: stringResource(R.string.product_search_title),
            leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
            leftIconContentDescription = stringResource(R.string.cd_back),
            onLeftIconClick = onBack,
        )

        androidx.compose.material3.OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onQueryChanged,
            placeholder = { Text(stringResource(R.string.product_search_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            singleLine = true,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            if (uiState.products.isEmpty()) {
                Text(
                    text = stringResource(R.string.product_search_empty),
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                LazyColumn {
                    items(uiState.products, key = { product: Product -> product.id }) { product: Product ->
                        ProductListItem(
                            product = product,
                            onClick = { onProductClicked(product) },
                        )
                    }
                }
            }
        }

        Button(
            onClick = onAddProductClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(stringResource(R.string.product_search_add_product))
        }
    }
}

@Composable
private fun ProductListItem(
    product: Product,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = product.name,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
        )
        Text(
            text = stringResource(R.string.product_search_kcal_per_100g, product.kcalPer100g),
        )
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}
