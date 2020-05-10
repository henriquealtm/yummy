package com.example.yummy.search.presentation

import com.example.yummy.search.domain.model.IngredientDomain
import com.example.yummy.search.presentation.model.IngredientPresentation

fun IngredientDomain.intoIngredientPresentation() = IngredientPresentation(
    name,
    unit
)

fun List<IngredientDomain>?.intoListIngredientPresentation() = this?.map { ingredientDomain ->
    ingredientDomain.intoIngredientPresentation()
}
