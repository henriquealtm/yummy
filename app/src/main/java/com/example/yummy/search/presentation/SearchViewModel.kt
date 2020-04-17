package com.example.yummy.search.presentation

import androidx.lifecycle.*
import com.example.core_ui.extension.addSameBehaviourSources

class SearchViewModel : ViewModel() {

    // Toolbar
    var actionTextPrefix: String? = null
    val actionText: LiveData<String>
    fun cleanFilters() {
        isHealthySelected.value = false
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

    val filteredFieldsCount = MediatorLiveData<Int>().apply {
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

    // Search Recipe
    private val mOnSearchRecipe = MutableLiveData(false)
    val onSearchRecipe: LiveData<Boolean>
        get() = mOnSearchRecipe

    fun searchRecipe() {
        mOnSearchRecipe.value = true
    }

    init {
        actionText = Transformations.map(filteredFieldsCount) { count ->
            "$actionTextPrefix $count"
        }
    }

}