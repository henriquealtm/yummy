package com.example.yummy.search.presentation

import androidx.lifecycle.MutableLiveData

fun getNewInitializedFoodIngredient() = FoodIngredient(
    MutableLiveData(),
    MutableLiveData(),
    MutableLiveData()
)