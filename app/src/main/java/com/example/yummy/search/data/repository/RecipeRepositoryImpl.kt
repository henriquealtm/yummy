package com.example.yummy.search.data.repository

import com.example.network.makeApiCall
import com.example.yummy.search.data.api.RecipeApi
import com.example.yummy.search.data.model.IngredientParams
import com.example.yummy.search.data.model.RecipeListData
import com.example.yummy.search.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi
) : RecipeRepository {

    override fun getRecipeList() = makeApiCall<RecipeListData>(
        recipeApi.listRepos()
    )

    override fun getIngredientParams() = makeApiCall<IngredientParams>(
        recipeApi.ingredientParams()
    )

}