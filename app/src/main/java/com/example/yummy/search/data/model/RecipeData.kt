package com.example.yummy.search.data.model

data class RecipeListData(
    val recipes: List<RecipeData>
)

data class RecipeData(
    val name: String?,
    val foodCategory: String?,
    val ingredients: List<IngredientAndAmountData>
)