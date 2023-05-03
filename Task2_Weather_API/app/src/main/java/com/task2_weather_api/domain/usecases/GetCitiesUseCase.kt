package com.task2_weather_api.domain.usecases

import com.task2_weather_api.data.repository.CityRepositoryImpl
import com.task2_weather_api.domain.model.CityModel

class GetCitiesUseCase(
    private val cityRepositoryImpl: CityRepositoryImpl
) {
    suspend operator fun invoke(
        lat: String,
        long: String,
        cnt: Int
    ): MutableList<CityModel> = cityRepositoryImpl.getCities(lat, long, cnt)
}
