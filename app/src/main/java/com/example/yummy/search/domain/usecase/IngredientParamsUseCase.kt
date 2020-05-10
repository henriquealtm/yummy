package com.example.yummy.search.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.network.Resource
import com.example.yummy.search.data.model.IngredientData
import com.example.yummy.search.domain.model.IngredientDomain
import com.example.yummy.search.domain.repository.RecipeRepository

class IngredientParamsUseCase(
    private val recipeRepository: RecipeRepository
) {

    operator fun invoke(): LiveData<Resource<List<IngredientDomain>>> =
        Transformations.map(recipeRepository.getIngredientParams()) { resource ->
            resource.resourceType {
                null
            }
        }


}