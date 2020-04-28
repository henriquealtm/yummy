package com.example.core_ui.extension.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T, R> MediatorLiveData<T>.addSameBehaviourSources(
    vararg sources: LiveData<R>,
    block: (R) -> Unit
) {
    sources.forEach { source ->
        addSource(source) {
            block(it)
        }
    }
}