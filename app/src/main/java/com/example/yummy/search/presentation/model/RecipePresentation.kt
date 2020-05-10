package com.example.yummy.search.presentation.model

data class RecipePresentation(
    val name: String,
    val foodCategory: String,
    val ingredientList: List<IngredientAndAmountPresentation>
)