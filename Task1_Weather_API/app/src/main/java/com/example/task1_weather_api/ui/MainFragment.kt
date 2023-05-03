package com.example.task1_weather_api.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.task1_weather_api.R
import com.example.task1_weather_api.data.DataContainer
import com.example.task1_weather_api.data.WeatherApi
import com.example.task1_weather_api.data.response.City
import com.example.task1_weather_api.repository.CityRepositoryImpl
import com.example.task1_weather_api.utils.hideKeyboard
import com.example.task1_weather_api.utils.showSnackbar
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_main_weather.*
import kotlinx.android.synthetic.main.fragment_main_weather.view.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main_weather){

    private var cityRepositoryImpl: CityRepositoryImpl = CityRepositoryImpl()
    private val api: WeatherApi = DataContainer.weatherApi


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RVAdapter()
        {
        }

        val recyclerView = rv_weather
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.btn_load.setOnClickListener {
            onLoadClick(adapter, view)
        }
        view.et_city.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onLoadClick(adapter, view)
            }
            true
        }
    }

    private fun loadRV(adapter:RVAdapter, cities: MutableList<City>?){
        if (cities != null) {
            adapter.setData(cities)
        }
    }

    private fun onLoadClick(adapter: RVAdapter, view: View) {
        view.et_city?.hideKeyboard()
        val query = view.et_city?.text.toString()
        loadWeather(query, adapter, view)
    }

    private fun loadWeather(query: String, adapter: RVAdapter, view: View) {
        lifecycleScope.launch {
            try {
                showLoading(true, view)
                api.getWeather(query).also {
                    showTemp(it.main.temp, view)
                    it.weather.firstOrNull()?.also {
                        showWeatherIcon(it.icon, view)
                    }
                }

                val string: String = getLatitudeAndLongitude()
                val wayLongitude =
                    string.substringBefore(" ").toDouble()//я без поянтия почему возвращается 0.0
                val wayLatitude =
                    string.substringAfter(" ").toDouble()//я без поянтия почему возвращается 0.0
                //к тому же смена gps точки не помогла и всё равно возвращается адрес офиса гугла
                val locality = api.getCities("55.830433", "49.066082", 11).list as MutableList<City>
                for(local in locality){
                    Log.d("LOCAL", local.name)
                }
                cityRepositoryImpl.setCities(locality)
                loadRV(adapter, getNearbyAddresses(query))
            } catch (error: Throwable) {
                showError(error, view)
            } finally {
                showLoading(false, view)
            }
        }
    }

    private fun showLoading(isShow: Boolean, view: View) {
        view.progress?.isVisible = isShow
    }

    private fun showError(error: Throwable, view: View) {
        view.findViewById<View>(android.R.id.content)
            ?.showSnackbar(error.message ?: "Error")
    }

    private fun showTemp(temp: Double, view: View) {//"Temp: $temp C"
        view.tv_hometown_temp.text = "Temp: $temp C"
        view.tv_hometown_temp?.isVisible = true
    }

    private fun showWeatherIcon(id: String, view: View) {
        Timber.e(id)
        view.iv_icon?.load("https://openweathermap.org/img/w/$id.png") {
            crossfade(true)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLatitudeAndLongitude(): String {
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        var temp1 = 0.0
        var temp2 = 0.0
        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            temp1 = location.latitude
            temp2 = location.longitude
        }
        return "$temp1 $temp2"
    }

    private fun getNearbyAddresses(query: String): MutableList<City>? {
        val falseCityQuery = "$query'"
        val cities = cityRepositoryImpl.getCities()
        val falseCity = cities?.find{
            city -> city.name == falseCityQuery
        }
        cities?.remove(falseCity)
        Log.d("790", cityRepositoryImpl.getCities()?.toList().toString())
        return cities
    }

}
