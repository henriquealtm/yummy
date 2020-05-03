package com.example.yummy.search.domain.repository

import androidx.lifecycle.LiveData
import com.example.network.Resource
import com.example.yummy.search.data.model.IngredientParams
import com.example.yummy.search.data.model.RecipeListData

interface RecipeRepository {

    fun getRecipeList(): LiveData<Resource<RecipeListData>>

    fun getIngredientParams(): LiveData<Resource<IngredientParams>>

}