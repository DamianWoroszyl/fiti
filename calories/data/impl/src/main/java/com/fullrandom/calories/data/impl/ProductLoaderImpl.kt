package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.calories.data.api.ProductLoader
import com.fullrandom.calories.localassets.api.AssetLoader
import com.fullrandom.calories.localassets.api.AvailableAssets.FOOD_DATA_BASE
import com.fullrandom.model.Product
import java.util.UUID
import javax.inject.Inject

private const val CSV_COLUMN_NAME = 0
private const val CSV_COLUMN_KCAL = 1
private const val CSV_COLUMN_PROTEIN = 2
private const val CSV_COLUMN_CARBS = 3
private const val CSV_COLUMN_FAT = 4
private const val CSV_EXPECTED_COLUMN_COUNT = 5

class ProductLoaderImpl @Inject constructor(
    private val assetLoader: AssetLoader,
    private val repository: CaloriesRepository,
) : ProductLoader {

    override suspend fun loadProducts() {
        val lines: List<String> = assetLoader.readLines(FOOD_DATA_BASE)
        val dataLines: List<String> = lines.drop(1)
        dataLines.forEach { line: String ->
            val product: Product? = parseProductLine(line)
            if (product != null) {
                repository.saveProduct(product)
            }
        }
    }

    private fun parseProductLine(line: String): Product? {
        val columns: List<String> = line.split(",")
        if (columns.size < CSV_EXPECTED_COLUMN_COUNT) return null
        return try {
            Product(
                id = UUID.randomUUID().toString(),
                name = columns[CSV_COLUMN_NAME].trim(),
                kcalPer100g = columns[CSV_COLUMN_KCAL].trim().toDouble(),
                proteinPer100g = columns[CSV_COLUMN_PROTEIN].trim().toDouble(),
                carbohydratesPer100g = columns[CSV_COLUMN_CARBS].trim().toDouble(),
                fatPer100g = columns[CSV_COLUMN_FAT].trim().toDouble(),
            )
        } catch (exception: NumberFormatException) {
            null
        }
    }
}
