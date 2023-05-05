package com.task2_weather_api.domain.usecases

import com.task2_weather_api.domain.repository.WeatherRepository
import com.task2_weather_api.domain.model.WeatherModel

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        query: String
    ): WeatherModel = weatherRepository.getWeather(query)
}
