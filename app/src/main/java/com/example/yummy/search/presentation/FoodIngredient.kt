package com.example.yummy.search.presentation

import androidx.lifecycle.MutableLiveData

data class FoodIngredient(
    val description: MutableLiveData<String>,
    val amount: MutableLiveData<String>,
    val amountType: MutableLiveData<String>
)