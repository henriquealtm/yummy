package com.example.yummy.search.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.core_ui.extension.util.handleOptional
import com.example.network.Resource
import com.example.yummy.search.domain.model.IngredientDomain
import com.example.yummy.search.domain.model.RecipeDomain
import com.example.yummy.search.domain.repository.RecipeRepository

class SearchUseCase(
    private val recipeRepository: RecipeRepository
) {

    operator fun invoke(): LiveData<Resource<List<RecipeDomain>>> =
        Transformations.map(recipeRepository.getRecipeList()) { resource ->
            resource.resourceType {
                resource.data?.recipeList?.map {
                    val ingredientDomainList = it.ingredientList.map { ingredient ->
                        IngredientDomain(
                            ingredient.name.handleOptional(),
                            ingredient.amount.handleOptional(),
                            ingredient.unit.handleOptional()
                        )
                    }

                    RecipeDomain(
                        it.name.handleOptional(),
                        it.foodCategory.handleOptional(),
                        ingredientDomainList.handleOptional()
                    )
                }
            }
        }


}