package com.example.yummy.search.domain.model

data class IngredientAndAmountDomain(
    val ingredient: IngredientDomain,
    val amount: Double
)

data class IngredientDomain(
    val name: String,
    val unit: String
)