package com.task2_weather_api.domain.usecases

import com.task2_weather_api.data.repository.GeoLocationRepositoryImpl
import com.task2_weather_api.di.DataContainer
import com.task2_weather_api.domain.location.DomainGeoLocation

class GetLocationUseCase(
    private val geoLocationRepositoryImpl: GeoLocationRepositoryImpl
) {
    suspend operator fun invoke(
    ): DomainGeoLocation = geoLocationRepositoryImpl.getLocation()
}
