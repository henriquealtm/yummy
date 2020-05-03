package com.example.yummy.search.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.core_ui.extension.util.handleOptional
import com.example.network.Resource
import com.example.yummy.search.domain.model.IngredientAndAmountDomain
import com.example.yummy.search.domain.model.IngredientDomain
import com.example.yummy.search.domain.model.RecipeDomain
import com.example.yummy.search.domain.repository.RecipeRepository

class SearchUseCase(
    private val recipeRepository: RecipeRepository
) {

    operator fun invoke(): LiveData<Resource<List<RecipeDomain>>> =
        Transformations.map(recipeRepository.getRecipeList()) { resource ->
            resource.resourceType {
                resource.data?.recipes?.map {
                    val ingredientDomainList = it.ingredients.map { item ->
                        IngredientAndAmountDomain(
                            IngredientDomain(
                                item.ingredient.name.handleOptional(),
                                item.ingredient.unit.handleOptional()
                            ),
                            item.amount.handleOptional()
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