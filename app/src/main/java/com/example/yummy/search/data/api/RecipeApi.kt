package com.example.yummy.search.data.api

import com.example.yummy.search.data.model.RecipeData
import com.example.yummy.search.data.model.RecipeListData
import retrofit2.Call
import retrofit2.http.GET

interface RecipeApi {

    @GET("recipe/list")
    fun listRepos(): Call<RecipeListData>

}