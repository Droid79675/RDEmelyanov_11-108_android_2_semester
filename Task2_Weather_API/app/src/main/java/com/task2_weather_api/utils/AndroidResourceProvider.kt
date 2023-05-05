package com.task2_weather_api.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

class AndroidResourceProvider(
    private val context: Context
) : ResourceProvider {

    @SuppressLint("MissingPermission")
    override fun getLatitudeAndLongitude(): String {
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        var temp1 = 0.0
        var temp2 = 0.0
        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            temp1 = location.latitude
            temp2 = location.longitude
        }
        return "$temp1 $temp2"
    }


}
