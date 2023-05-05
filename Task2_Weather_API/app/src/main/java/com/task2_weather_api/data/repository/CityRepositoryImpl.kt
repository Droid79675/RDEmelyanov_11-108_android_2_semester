package com.task2_weather_api.data.repository

import com.task2_weather_api.data.WeatherApi
import com.task2_weather_api.data.mapper.toCityList
import com.task2_weather_api.data.response.City
import com.task2_weather_api.domain.model.CityModel
import com.task2_weather_api.domain.repository.CityRepository

class CityRepositoryImpl(
    private val api: WeatherApi
) :
    CityRepository {

    override suspend fun getCities(
        lat: String, long: String, cnt: Int
    ): MutableList<CityModel> = api.getCities(lat, long, cnt).toCityList()

    private var cityList: MutableList<City>? = null
    override fun getCities(): MutableList<City>? {
        return cityList
    }

    override fun setCities(cityList: MutableList<City>) {
        this.cityList = cityList.subList(0, 11)
    }


}
