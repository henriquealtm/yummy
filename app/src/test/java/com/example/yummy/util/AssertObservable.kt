package com.example.yummy.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.core_ui.extension.util.handleOptional
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun <T> assertEqualsObservable(
    liveData: LiveData<T>,
    observer: Observer<T>,
    actual: T
) {
    assert(liveData, observer, { assertEquals(liveData.value, actual) })
}

fun <T> assertNullObservable(
    liveData: LiveData<T>,
    observer: Observer<T>
) {
    assert(liveData, observer, { assertNull(liveData.value) })
}

fun assertTrueObservable(
    liveData: LiveData<Boolean>,
    observer: Observer<Boolean>
) {
    assert(liveData, observer, { assertTrue(liveData.value.handleOptional()) })
}

fun assertFalseObservable(
    liveData: LiveData<Boolean>,
    observer: Observer<Boolean>
) {
    assert(liveData, observer, { assertFalse(liveData.value.handleOptional()) })
}

private fun <T> assert(
    liveData: LiveData<T>,
    observer: Observer<T>,
    assertBlock: () -> Unit
) {
    liveData.observeForever(observer)
    assertBlock.invoke()
    liveData.removeObserver(observer)

}