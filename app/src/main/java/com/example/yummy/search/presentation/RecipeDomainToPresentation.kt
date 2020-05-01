package com.example.yummy.search.presentation

import com.example.core_ui.extension.util.handleOptional
import com.example.yummy.search.domain.model.RecipeDomain
import com.example.yummy.search.presentation.model.IngredientPresentation
import com.example.yummy.search.presentation.model.RecipePresentation

fun RecipeDomain.intoRecipePresentation(): RecipePresentation {
    val ingredientPresentation = ingredientList.map { ingredient ->
        IngredientPresentation(
            ingredient.name,
            ingredient.amount.toString(),
            ingredient.unit
        )
    }

    return RecipePresentation(
        name,
        foodCategory,
        ingredientPresentation.handleOptional()
    )
}

fun List<RecipeDomain>?.intoListRecipePresentation(): List<RecipePresentation>? =
    this?.map { recipeDomain ->
        recipeDomain.intoRecipePresentation()
    }
