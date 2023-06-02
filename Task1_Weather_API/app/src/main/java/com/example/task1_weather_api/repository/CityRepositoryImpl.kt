package com.example.task1_weather_api.repository

import com.example.task1_weather_api.data.response.City

class CityRepositoryImpl: CityRepository {
    private var cityList: MutableList<City>? = null
    override fun getCities(): MutableList<City>? {
        return cityList
    }

    override fun setCities(cityList: MutableList<City>) {
        this.cityList = cityList.subList(0, 11)
    }

}
