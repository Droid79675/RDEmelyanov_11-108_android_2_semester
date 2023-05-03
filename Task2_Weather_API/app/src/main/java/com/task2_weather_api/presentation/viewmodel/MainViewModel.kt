package com.task2_weather_api.presentation.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.task2_weather_api.di.DataContainer
import com.task2_weather_api.domain.location.DomainGeoLocation
import com.task2_weather_api.domain.model.CityModel
import com.task2_weather_api.domain.model.WeatherModel
import com.task2_weather_api.domain.usecases.GetCitiesUseCase
import com.task2_weather_api.domain.usecases.GetLocationUseCase
import com.task2_weather_api.domain.usecases.GetWeatherUseCase
import com.task2_weather_api.presentation.WeatherRVAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _weatherModel = MutableLiveData<WeatherModel?>(null)
    val weatherModel: LiveData<WeatherModel?>
        get() = _weatherModel

    private val _cityList = MutableLiveData<MutableList<CityModel>?>(null)
    val cityList: LiveData<MutableList<CityModel>?>
        get() = _cityList

    private val _weatherRVAdapter = MutableLiveData<WeatherRVAdapter?>(null)
    val weatherRVAdapter: LiveData<WeatherRVAdapter?>
        get() = _weatherRVAdapter

    private val _combinedCityListWeatherRVAdapter =
        MediatorLiveData<Pair<MutableList<CityModel>?, WeatherRVAdapter?>>()
    val combinedCityListWeatherRVAdapter: MediatorLiveData<Pair<MutableList<CityModel>?, WeatherRVAdapter?>>
        get() = _combinedCityListWeatherRVAdapter

    val navigateDetails = MutableLiveData<Boolean?>(null)

    fun onLoadClick(query: String, recyclerView: WeatherRVAdapter) {
        loadWeather(query, recyclerView)
    }

    private fun loadWeather(query: String, recyclerView: WeatherRVAdapter) {
        viewModelScope.launch {
            try {
                _loading.value = true
                getWeatherUseCase(query).also { weatherModel ->
                    _weatherModel.value = weatherModel
                }
                val domainGeoLocation: DomainGeoLocation = getLocationUseCase()
                //координаты вручную введены, поскольку смена gps точки не помогла и всё равно возвращается адрес офиса гугла
                getCitiesUseCase("55.830433", "49.066082", 11).also { cityList ->
                    _cityList.value = findFalseCity(query, cityList)
                }

                _weatherRVAdapter.value = recyclerView

                _combinedCityListWeatherRVAdapter.apply {
                    addSource(_cityList) { value = Pair(it, _weatherRVAdapter.value) }
                    addSource(_weatherRVAdapter) { value = Pair(_cityList.value, it) }
                    Timber.d("ЗАЭПЛАЯЛ")
                }
            } catch (error: Throwable) {
                _error.value = error
            } finally {
                _loading.value = false
            }
        }
    }

    private fun findFalseCity(
        query: String,
        cityList: MutableList<CityModel>
    ): MutableList<CityModel> {
        val falseCityQuery = "$query'"
        val falseCity = cityList.find { city ->
            city.name == falseCityQuery
        }
        cityList.remove(falseCity)
        return cityList
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val weatherUseCase = DataContainer.weatherUseCase
                val cityUseCase = DataContainer.cityUseCase
                val locationUseCase = DataContainer.locationUseCase
                return MainViewModel(weatherUseCase, cityUseCase, locationUseCase) as T
            }
        }

        val FactoryExt: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val weatherUseCase = DataContainer.weatherUseCase
                val cityUseCase = DataContainer.cityUseCase
                val locationUseCase = DataContainer.locationUseCase
                MainViewModel(weatherUseCase, cityUseCase, locationUseCase)
            }
        }
    }
}
