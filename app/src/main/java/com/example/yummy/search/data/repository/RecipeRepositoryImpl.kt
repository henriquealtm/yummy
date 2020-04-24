package com.example.yummy.search.data.repository

import androidx.lifecycle.LiveData
import com.example.network.Resource
import com.example.network.makeApiCall
import com.example.yummy.search.data.api.RecipeApi
import com.example.yummy.search.data.model.RecipeData
import com.example.yummy.search.data.model.RecipeListData
import com.example.yummy.search.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi
) : RecipeRepository {

    override fun getRecipeList(): LiveData<Resource<RecipeListData>> = makeApiCall(
        recipeApi.listRepos()
    )

}