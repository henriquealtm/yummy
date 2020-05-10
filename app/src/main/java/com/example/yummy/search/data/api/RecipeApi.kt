package com.example.yummy.search.data.api

import com.example.yummy.search.data.model.IngredientParams
import com.example.yummy.search.data.model.RecipeListData
import retrofit2.Call
import retrofit2.http.GET

interface RecipeApi {

    @GET("recipes")
    fun listRepos(): Call<RecipeListData>

    @GET("search/parameters")
    fun ingredientParams(): Call<IngredientParams>

}