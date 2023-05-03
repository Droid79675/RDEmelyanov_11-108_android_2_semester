package com.task2_weather_api.data.repository

import com.google.android.gms.location.FusedLocationProviderClient
import com.task2_weather_api.data.location.DataGeoLocation
import com.task2_weather_api.domain.location.DomainGeoLocation
import com.task2_weather_api.domain.repository.GeoLocationRepository

class GeoLocationRepositoryImpl(
    private val dataGeoLocation: DataGeoLocation
) : GeoLocationRepository {

    override suspend fun getLocation(): DomainGeoLocation = dataGeoLocation.getDataLocation()
}
