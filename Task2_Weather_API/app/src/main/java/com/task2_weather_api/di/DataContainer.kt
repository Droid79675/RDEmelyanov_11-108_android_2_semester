package com.task2_weather_api.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.task2_weather_api.BuildConfig
import com.task2_weather_api.data.WeatherApi
import com.task2_weather_api.data.interceptor.ApiKeyInterceptor
import com.task2_weather_api.data.location.DataGeoLocation
import com.task2_weather_api.data.repository.CityRepositoryImpl
import com.task2_weather_api.data.repository.GeoLocationRepositoryImpl
import com.task2_weather_api.data.repository.WeatherRepositoryImpl
import com.task2_weather_api.domain.usecases.GetCitiesUseCase
import com.task2_weather_api.domain.usecases.GetLocationUseCase
import com.task2_weather_api.domain.usecases.GetWeatherUseCase
import com.task2_weather_api.utils.AndroidResourceProvider
import com.task2_weather_api.utils.ContextProvider
import com.task2_weather_api.utils.ResourceProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

object DataContainer {

    private const val BASE_URL = BuildConfig.API_ENDPOINT

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi = retrofit.create(WeatherApi::class.java)

    private val fusedLocationProviderClient: FusedLocationProviderClient
        get() = LocationServices.getFusedLocationProviderClient(ContextProvider.getApplication().applicationContext)

    private val weatherRepository = WeatherRepositoryImpl(weatherApi)
    private val cityRepositoryImpl = CityRepositoryImpl(weatherApi)
    private val geoLocationRepositoryImpl = GeoLocationRepositoryImpl(dataGeoLocation)

    val weatherUseCase: GetWeatherUseCase
        get() = GetWeatherUseCase(weatherRepository)
    val cityUseCase: GetCitiesUseCase
        get() = GetCitiesUseCase(cityRepositoryImpl)
    val locationUseCase: GetLocationUseCase
        get() = GetLocationUseCase(geoLocationRepositoryImpl)

    fun provideResources(
        applicationContext: Context
    ): ResourceProvider = AndroidResourceProvider(applicationContext)

    private val dataGeoLocation: DataGeoLocation
        get() = DataGeoLocation(
            fusedLocationProviderClient
        )
}
