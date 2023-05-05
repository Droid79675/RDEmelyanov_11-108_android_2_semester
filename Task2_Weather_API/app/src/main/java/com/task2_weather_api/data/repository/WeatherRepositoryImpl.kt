package com.task2_weather_api.data.repository

import com.task2_weather_api.data.WeatherApi
import com.task2_weather_api.data.mapper.toWeatherModel
import com.task2_weather_api.domain.model.WeatherModel
import com.task2_weather_api.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(
        query: String
    ): WeatherModel = api.getWeather(query).toWeatherModel()
}

