package com.example.yummy.search.domain.model

data class RecipeDomain(
    val name: String,
    val foodCategory: String,
    val ingredientList: List<IngredientDomain>
)