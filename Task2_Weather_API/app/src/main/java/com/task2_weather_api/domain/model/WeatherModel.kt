package com.task2_weather_api.domain.model

data class WeatherModel (
    val temperature: Double,
    val windSpeed: Double,
    val windDeg: Int,
    val humidity: Int,
    val icon: String
)
