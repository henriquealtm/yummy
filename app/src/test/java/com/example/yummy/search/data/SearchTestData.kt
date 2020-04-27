package com.example.yummy.search.data

import com.example.yummy.search.domain.model.IngredientDomain
import com.example.yummy.search.domain.model.RecipeDomain

object SearchTestData {

    private const val recipeName = "Sugar"
    private const val foodCategory = "DESSERT"

    private const val ingredientName = "Sugar"
    private const val ingredientAmount = 1.0
    private const val ingredientUnit = "Kg"
    private val ingredientList = listOf(
        IngredientDomain(
            ingredientName, ingredientAmount, ingredientUnit
        )
    )

    private val recipeDomain = RecipeDomain(recipeName, foodCategory, ingredientList)

    val recipeDomainList = listOf(recipeDomain)

}