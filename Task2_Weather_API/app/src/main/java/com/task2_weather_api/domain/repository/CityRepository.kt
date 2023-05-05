package com.task2_weather_api.domain.repository

import com.task2_weather_api.data.response.City
import com.task2_weather_api.domain.model.CityModel

interface CityRepository {

    suspend fun getCities(lat: String, long: String, cnt: Int): MutableList<CityModel>

    fun getCities(): MutableList<City>?

    fun setCities(cityList: MutableList<City>)

}
