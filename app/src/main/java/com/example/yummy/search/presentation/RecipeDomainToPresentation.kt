package com.example.yummy.search.presentation

import com.example.core_ui.extension.util.handleOptional
import com.example.yummy.search.domain.model.RecipeDomain
import com.example.yummy.search.presentation.model.IngredientAndAmountPresentation
import com.example.yummy.search.presentation.model.IngredientPresentation
import com.example.yummy.search.presentation.model.RecipePresentation

fun RecipeDomain.intoRecipePresentation(): RecipePresentation {
    val ingredientPresentation = ingredientList.map { item ->
        IngredientAndAmountPresentation(
            IngredientPresentation(
                item.ingredient.name,
                item.ingredient.unit
            ),
            item.amount.toString()
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
