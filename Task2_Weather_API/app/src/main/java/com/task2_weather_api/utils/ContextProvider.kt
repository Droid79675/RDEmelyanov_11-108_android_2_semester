package com.task2_weather_api.utils

import android.app.Application

object ContextProvider {
    private lateinit var application: Application

    fun setApplication(getApplication: Application){
        application = getApplication
    }

    fun getApplication(): Application{
        return application
    }
}
