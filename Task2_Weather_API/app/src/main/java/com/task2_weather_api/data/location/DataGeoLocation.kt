package com.task2_weather_api.data.location

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.task2_weather_api.domain.location.DomainGeoLocation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DataGeoLocation(
    private val locationProviderClient: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    suspend fun getDataLocation(): DomainGeoLocation = suspendCancellableCoroutine<DomainGeoLocation> { emitter ->
        locationProviderClient.lastLocation
            .addOnSuccessListener {
                emitter.resume(
                    DomainGeoLocation(
                        lat = it.latitude,
                        long = it.longitude
                    )
                )
            }
            .addOnFailureListener {
                emitter.resumeWithException(it)
            }
    }
}
