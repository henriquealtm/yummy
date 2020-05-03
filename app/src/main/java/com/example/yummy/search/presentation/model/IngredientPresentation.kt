package com.example.yummy.search.presentation.model

data class IngredientAndAmountPresentation(
    val ingredient: IngredientPresentation,
    val amount: String
)

data class IngredientPresentation(
    val ingredient: String,
    val unit: String
)