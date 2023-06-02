package com.example.task1_weather_api.repository

import com.example.task1_weather_api.data.response.City

interface CityRepository {

    fun getCities(): MutableList<City>?

    fun setCities(cityList: MutableList<City>)
}
