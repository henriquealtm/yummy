package com.example.yummy.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.test.assertEquals

fun <T> assertObservable(
    liveData: LiveData<T>,
    observer: Observer<T>,
    actual: T?
) {
    liveData.observeForever(observer)
    assertEquals(liveData.value, actual)
    liveData.removeObserver(observer)
}