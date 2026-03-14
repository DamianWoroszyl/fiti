package com.fullrandom.calories.data.impl

import com.fullrandom.calories.data.api.CaloriesRepository
import com.fullrandom.calories.data.api.ProductLoader
import com.fullrandom.calories.localassets.api.AssetLoader
import com.fullrandom.calories.localassets.api.AvailableAssets.FOOD_DATA_BASE
import com.fullrandom.model.Product
import java.util.UUID
import javax.inject.Inject

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
            val name: String = columns.dropLast(4).joinToString(",").trim().removeSurrounding("\"")
            val kcalPer100g: Double = columns[columns.size - 4].trim().toDouble()
            val proteinPer100g: Double = columns[columns.size - 3].trim().toDouble()
            val carbohydratesPer100g: Double = columns[columns.size - 2].trim().toDouble()
            val fatPer100g: Double = columns[columns.size - 1].trim().toDouble()
            Product(
                id = UUID.randomUUID().toString(),
                name = name,
                kcalPer100g = kcalPer100g,
                proteinPer100g = proteinPer100g,
                carbohydratesPer100g = carbohydratesPer100g,
                fatPer100g = fatPer100g,
            )
        } catch (exception: NumberFormatException) {
            null
        }
    }
}
