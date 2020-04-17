package com.example.yummy.utils.di

import com.example.yummy.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel() }
}

val loadSearchModule by lazy {
    loadKoinModules(searchModule)
}

fun loadSearchModule() = loadSearchModule