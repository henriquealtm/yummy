package com.example.yummy.search.presentation

import androidx.lifecycle.*
import com.example.core_ui.extension.addSameBehaviourSources
import com.example.core_ui.extension.handleOptional
import com.example.core_ui.extension.plusAssign
import com.example.widget.progressbutton.ProgressButtonState
import androidx.lifecycle.MutableLiveData
import com.example.network.Resource
import com.example.yummy.search.domain.usecase.SearchUseCase
import com.example.yummy.search.presentation.model.IngredientPresentation
import com.example.yummy.search.presentation.model.RecipePresentation

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    // Toolbar
    var actionTextPrefix: String? = null

    val actionText: LiveData<String>

    fun cleanFilters() {
        deselectAllFoodCategories()

        removeAllFoodIngredients()

        addNewEmptyIngredient()
    }

    // Navigate Back
    private val mOnNavigateBack = MutableLiveData(false)
    val onNavigateBack: LiveData<Boolean>
        get() = mOnNavigateBack

    fun navigateBack() {
        mOnNavigateBack.value = true
    }

    // Food Category
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

    // IngredientData
    private val mFirstIngredientItem = FoodIngredient(
        MutableLiveData(),
        MutableLiveData(),
        MutableLiveData()
    )

    val foodIngredientList = MutableLiveData(mutableListOf(mFirstIngredientItem))

    private fun removeAllFoodIngredients() {
        foodIngredientList.value?.removeAll { true }
    }

    private val mIngredientFilteredFieldsCount =
        Transformations.map(foodIngredientList) { list ->
            list.filter {
                it.description.value?.isNotEmpty().handleOptional()
            }.count()
        }
    val ingredientFilteredFieldsCount: LiveData<Int>
        get() = mIngredientFilteredFieldsCount

    // TODO Henrique - This mShowCannotAddIngredientMessage and canAddNewIngredient is not being
    // TODO Henrique - unit tested because they are gonna be removed
    private val mShowCannotAddIngredientMessage = MutableLiveData(false)
    val showCannotAddIngredientMessage
        get() = mShowCannotAddIngredientMessage

    fun addNewEmptyIngredient() {
        if (canAddNewIngredient()) {
            foodIngredientList += FoodIngredient(
                MutableLiveData(),
                MutableLiveData(),
                MutableLiveData()
            )
        } else {
            showCannotAddIngredientMessage.value = true
        }
    }

    private fun canAddNewIngredient() =
        foodIngredientList.value?.isEmpty().handleOptional() ||
                foodIngredientList.value?.lastOrNull()?.run {
                    this.description.value.isNullOrEmpty().not() && this.amount.value.isNullOrEmpty().not()
                }.handleOptional()

    // Total Filtered Fields
    private val mTotalFilteredFields = MediatorLiveData<Int>().apply {
        value = 0

        addSource(mCategoryFilteredFieldsCount) { categoryFilteredCount ->
            categoryFilteredCount?.let {
                value = getTotalFilteredValues()
            }
        }

        addSource(mIngredientFilteredFieldsCount) { ingredientFilteredCount ->
            ingredientFilteredCount?.let {
                value = getTotalFilteredValues()
            }
        }
    }
    val totalFilteredFields: LiveData<Int>
        get() = mTotalFilteredFields

    private fun getTotalFilteredValues() =
        mCategoryFilteredFieldsCount.value.handleOptional() +
                mIngredientFilteredFieldsCount.value.handleOptional()

    // Search RecipeData
    private val mOnSearchRecipe = MutableLiveData(false)

    fun searchRecipe() {
        mOnSearchRecipe.value = true
    }

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

    private fun getButtonStateBasedOnFilteredFields(count: Int) = if (count > 0) {
        ProgressButtonState.ENABLED
    } else {
        ProgressButtonState.DISABLED
    }

    val searchButtonState: LiveData<ProgressButtonState>
        get() = mSearchButtonState


    init {
        actionText = Transformations.map(mTotalFilteredFields) { count ->
            "$actionTextPrefix $count"
        }
    }

}