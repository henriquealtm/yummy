package com.example.yummy.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    // Navigate Back
    private val mOnNavigateBack = MutableLiveData(false)
    val onNavigateBack: LiveData<Boolean>
        get() = mOnNavigateBack

    fun navigateBack() {
        mOnNavigateBack.value = true
    }


    // Search Recipe
    private val mOnSearchRecipe = MutableLiveData(false)
    val onSearchRecipe: LiveData<Boolean>
        get() = mOnSearchRecipe

    fun searchRecipe() {
        mOnSearchRecipe.value = true
    }

}