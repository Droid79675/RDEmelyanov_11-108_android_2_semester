package com.task2_weather_api.data.mapper

import com.task2_weather_api.data.response.CitiesResponse
import com.task2_weather_api.data.response.WeatherResponse
import com.task2_weather_api.domain.model.CityModel
import com.task2_weather_api.domain.model.WeatherModel

fun WeatherResponse.toWeatherModel(): WeatherModel = WeatherModel(
    temperature = main.temp,
    windSpeed = wind.speed,
    windDeg = wind.deg,
    humidity = main.humidity,
    icon = weather.first().icon
)

fun CitiesResponse.toCityList(): MutableList<CityModel> {
    val cityList: MutableList<CityModel> = mutableListOf()
    for (city in list) {
        val cityModel = CityModel(
            name = city.name,
            temp = city.main.temp.toString(),
            deg = city.wind.deg.toString(),
            gust = city.wind.gust.toString(),
            speed = city.wind.speed.toString(),
            humidity = city.main.humidity.toString(),
            pressure = city.main.pressure.toString(),
            sunrise = city.sys.sunrise.toString(),
            sunset = city.sys.sunset.toString(),
            icon = city.weather.first().icon
        )
        cityList.add(cityModel)
    }
    return cityList
}

fun List<WeatherResponse>.toWeathers(): List<WeatherModel> = map {
    it.toWeatherModel()
}

//fun List<CitiesResponse>.toCities(): List<CityModel> = map {
//    it.toCityList()
//}

