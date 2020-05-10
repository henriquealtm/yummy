package com.example.yummy.search.data

import com.example.yummy.search.domain.model.IngredientAndAmountDomain
import com.example.yummy.search.domain.model.IngredientDomain
import com.example.yummy.search.domain.model.RecipeDomain
import com.example.yummy.search.presentation.intoListIngredientPresentation
import com.example.yummy.search.presentation.intoListRecipePresentation

object SearchTestData {

    // Generic Data
    private const val ingredientName = "Sugar"
    private const val ingredientUnit = "Kg"
    private val ingredientDomain = IngredientDomain(ingredientName, ingredientUnit)

    // Ingredient Params Data
    val ingredientParamsDomain = listOf(ingredientDomain)

    val ingredientParamsPresentation = ingredientParamsDomain.intoListIngredientPresentation()

    // Search Recipe Data
    private const val recipeName = "Sugar"
    private const val foodCategory = "DESSERT"
    private const val ingredientAmount = 1.0

    private val ingredientAndAmountList = listOf(
        IngredientAndAmountDomain(
            IngredientDomain(ingredientName, ingredientUnit),
            ingredientAmount
        )
    )

    private val recipeDomain = RecipeDomain(recipeName, foodCategory, ingredientAndAmountList)

    val recipeDomainList = listOf(recipeDomain)

    val recipePresentationList = recipeDomainList.intoListRecipePresentation()

}