package com.example.yummy.application

import android.app.Application
import com.example.yummy.utils.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YummyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@YummyApplication)
            modules(searchModule)
        }

    }

}