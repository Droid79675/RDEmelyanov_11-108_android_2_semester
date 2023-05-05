package com.task2_weather_api.domain.repository

import com.task2_weather_api.domain.model.WeatherModel

interface WeatherRepository {
    suspend fun getWeather(query: String): WeatherModel
}
