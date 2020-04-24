package com.example.yummy.search.data.model

data class RecipeData(
    val name: String?,
    val foodCategory: String?,
    val ingredientList: List<IngredientData>
)