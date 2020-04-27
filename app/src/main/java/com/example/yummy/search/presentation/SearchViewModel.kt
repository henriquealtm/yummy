package com.example.yummy.search.presentation

import androidx.lifecycle.*
import com.example.core_ui.extension.addSameBehaviourSources
import com.example.core_ui.extension.handleOptional
import com.example.network.Resource
import com.example.widget.progressbutton.ProgressButtonState
import com.example.yummy.search.domain.usecase.SearchUseCase
import com.example.yummy.search.presentation.model.IngredientPresentation
import com.example.yummy.search.presentation.model.RecipePresentation

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    /** Toolbar Section */
    var actionTextPrefix: String? = null

    val actionText: LiveData<String>

    fun cleanFilters() {
        deselectAllFoodCategories()

        removeAllIngredients.value = true

        mustAddNewIngredient.value = true
    }

    private val mOnNavigateBack = MutableLiveData(false)
    val onNavigateBack: LiveData<Boolean>
        get() = mOnNavigateBack

    fun navigateBack() {
        mOnNavigateBack.value = true
    }

    /** Food Category Section */
    val isHealthySelected = MutableLiveData<Boolean>(false)
    val isDessertSelected = MutableLiveData<Boolean>(false)
    val isSnackSelected = MutableLiveData<Boolean>(false)
    val isMainCourseSelected = MutableLiveData<Boolean>(false)
    val isFastFoodSelected = MutableLiveData<Boolean>(false)

    private fun deselectAllFoodCategories() {
        isHealthySelected.value = false
        isDessertSelected.value = false
        isSnackSelected.value = false
        isMainCourseSelected.value = false
        isFastFoodSelected.value = false
    }

    private val mCategoryFilteredFieldsCount = MediatorLiveData<Int>().apply {
        value = 0

        addSameBehaviourSources(
            isHealthySelected,
            isDessertSelected,
            isSnackSelected,
            isMainCourseSelected,
            isFastFoodSelected
        ) { isSelected ->
            value = if (isSelected) {
                value?.inc()
            } else {
                value?.takeIf { it > 0 }?.dec() ?: 0
            }
        }
    }
    val categoryFilteredFieldsCount: LiveData<Int>
        get() = mCategoryFilteredFieldsCount

    /** Ingredient Section */
    // Ingredient List
    val ingredientList = mutableListOf(getNewInitializedFoodIngredient())

    // Remove all Ingredients
    private val removeAllIngredients = MutableLiveData<Boolean>(false)

    // Add new Ingredient
    var mustAddNewIngredient = MediatorLiveData<Boolean>().apply {
        value = false
    }

    var descriptionLastSource: LiveData<String>? = null

    private fun updateMustAddNewIngredientSource() {
        descriptionLastSource?.let {
            mustAddNewIngredient.removeSource(it)
        }

        descriptionLastSource = ingredientList.lastOrNull()?.description

        descriptionLastSource?.let {
            mustAddNewIngredient.addSource(it) { description ->
                if (description.isNotEmpty()) {
                    mustAddNewIngredient.value = true
                }
            }
        }
    }

    val ingredientUpdatedList = MediatorLiveData<MutableList<FoodIngredient>>().apply {
        value = ingredientList

        addSource(mustAddNewIngredient) { mustAdd ->
            if (mustAdd) {
                ingredientList.add(getNewInitializedFoodIngredient())
                value = ingredientList
                updateMustAddNewIngredientSource()
            }
        }

        addSource(removeAllIngredients) { mustRemove ->
            if (mustRemove) {
                ingredientList.removeAll { true }
                value = ingredientList
            }
        }
    }

    private val mIngredientFilteredFieldsCount =
        Transformations.map(ingredientUpdatedList) { list ->
            list.filter {
                it.description.value?.isNotEmpty().handleOptional()
            }.count()
        }
    val ingredientFilteredFieldsCount: LiveData<Int>
        get() = mIngredientFilteredFieldsCount

    /** Total Filtered Fields Section */
    private val mTotalFilteredFields = MediatorLiveData<Int>().apply {
        value = 0

        addSameBehaviourSources(
            mCategoryFilteredFieldsCount,
            mIngredientFilteredFieldsCount
        ) {
            value = getTotalFilteredValues()
        }
    }
    val totalFilteredFields: LiveData<Int>
        get() = mTotalFilteredFields

    private fun getTotalFilteredValues() =
        mCategoryFilteredFieldsCount.value.handleOptional() +
                mIngredientFilteredFieldsCount.value.handleOptional()

    /** Search Recipe Section */
    private val mOnSearchRecipe = MutableLiveData(false)

    fun searchRecipe() {
        mOnSearchRecipe.value = true
    }

    // TODO Henrique - Refactor this switchMap/map horror
    val recipeResult: LiveData<Resource<List<RecipePresentation>>> =
        Transformations.switchMap(mOnSearchRecipe) { mustSearchRecipe ->
            mustSearchRecipe?.takeIf { it }?.let {
                Transformations.map(searchUseCase()) { resource ->
                    resource.resourceType {
                        resource?.data?.map {
                            val ingredientPresentation = it.ingredientList.map { ingredient ->
                                IngredientPresentation(
                                    ingredient.name,
                                    ingredient.amount.toString(),
                                    ingredient.unit
                                )
                            }

                            RecipePresentation(
                                it.name,
                                it.foodCategory,
                                ingredientPresentation.handleOptional()
                            )
                        }
                    }
                }
            } ?: MutableLiveData<Resource<List<RecipePresentation>>>(null)
        }

    private val mSearchButtonState = MediatorLiveData<ProgressButtonState>().apply {
        value = ProgressButtonState.DISABLED

        addSource(mCategoryFilteredFieldsCount) { count ->
            value = getButtonStateBasedOnFilteredFields(count)
        }

        addSource(recipeResult) { resource ->
            value = if (resource is Resource.Loading<*>) {
                ProgressButtonState.LOADING
            } else {
                getButtonStateBasedOnFilteredFields(
                    mCategoryFilteredFieldsCount.value.handleOptional()
                )
            }
        }
    }
    val searchButtonState: LiveData<ProgressButtonState>
        get() = mSearchButtonState

    private fun getButtonStateBasedOnFilteredFields(count: Int) = if (count > 0) {
        ProgressButtonState.ENABLED
    } else {
        ProgressButtonState.DISABLED
    }

    init {
        actionText = Transformations.map(mTotalFilteredFields) { count ->
            "$actionTextPrefix $count"
        }

        updateMustAddNewIngredientSource()
    }

}