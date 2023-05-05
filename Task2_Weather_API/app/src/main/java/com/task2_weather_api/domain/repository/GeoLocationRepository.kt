package com.task2_weather_api.domain.repository

import com.task2_weather_api.domain.location.DomainGeoLocation

interface GeoLocationRepository {
    suspend fun getLocation(): DomainGeoLocation
}
