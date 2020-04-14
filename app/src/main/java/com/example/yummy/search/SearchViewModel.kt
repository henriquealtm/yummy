package com.example.yummy.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val mOnSearchRecipe = MutableLiveData(false)
    val onSearchRecipe: LiveData<Boolean>
        get() = mOnSearchRecipe

    fun searchRecipe() {
        mOnSearchRecipe.value = true
    }

}