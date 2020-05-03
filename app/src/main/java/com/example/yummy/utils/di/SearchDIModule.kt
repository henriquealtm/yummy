package com.example.yummy.utils.di

import com.example.network.createNetworkClient
import com.example.yummy.search.data.api.RecipeApi
import com.example.yummy.search.data.repository.RecipeRepositoryImpl
import com.example.yummy.search.domain.repository.RecipeRepository
import com.example.yummy.search.domain.usecase.IngredientParamsUseCase
import com.example.yummy.search.domain.usecase.SearchUseCase
import com.example.yummy.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val searchModule = module {
    viewModel {
        SearchViewModel(
            searchUseCase = get(),
            ingredientParamsUseCase = get()
        )
    }

    factory { SearchUseCase(recipeRepository = get()) }

    factory { IngredientParamsUseCase(recipeRepository = get()) }

    single { RecipeRepositoryImpl(getRecipeApi()) } bind RecipeRepository::class
}

private fun getRecipeApi() = createNetworkClient().create(RecipeApi::class.java)