package com.example.yummy.search.data.model

data class IngredientParams(
    val ingredients: List<IngredientData>
)

data class IngredientAndAmountData(
    val ingredient: IngredientData,
    val amount: Double
)

data class IngredientData(
    val name: String?,
    val unit: String?
)