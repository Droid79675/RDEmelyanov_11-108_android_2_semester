package com.example.task1_weather_api

import android.app.Application
import androidx.viewbinding.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
