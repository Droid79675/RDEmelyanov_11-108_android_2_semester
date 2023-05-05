package com.task2_weather_api.data

import com.task2_weather_api.data.response.CitiesResponse
import com.task2_weather_api.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
    ): WeatherResponse

    @GET("find")
    suspend fun getCities(
        @Query("lat") lat: String,
        @Query("lon") long: String,
        @Query("cnt") cnt: Int,
        @Query("units") units: String = "metric"
    ): CitiesResponse
}
