package com.fullrandom.model

data class Dish(
    val id: String,
    val name: String,
    val products: List<DishProduct>
)