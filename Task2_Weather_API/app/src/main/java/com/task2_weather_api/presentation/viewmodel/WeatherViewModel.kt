package com.task2_weather_api.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.task2_weather_api.domain.model.CityModel
import com.task2_weather_api.presentation.WeatherFragment
import timber.log.Timber

class WeatherViewModel() : ViewModel() {

    private val _cityModel = MutableLiveData<CityModel>(null)
    val cityModel: LiveData<CityModel>
        get() = _cityModel

    fun setCityModel(city: CityModel?) {
        try {
            _cityModel.value = city!!
        } catch (e: Error) {
            Timber.tag("WEATHERVIEWMODEL").d(e.toString())
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return WeatherViewModel() as T
            }
        }
    }

}
