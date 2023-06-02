package com.example.task1_weather_api.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import com.example.task1_weather_api.R
import com.example.task1_weather_api.model.CityModel
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.item_weather.tv_name

class WeatherFragment: Fragment(R.layout.fragment_weather) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cityModel: CityModel? = arguments?.getParcelable<CityModel>(ARG_TEXT)

        tv_name.text = cityModel?.name
        tv_deg.text = "Degrees: " + cityModel?.deg
        tv_gust.text = "Gust: " + cityModel?.gust
        tv_speed.text = "Speed: " + cityModel?.speed
        tv_humidity.text = "Humidity: " + cityModel?.humidity
        tv_pressure.text = "Pressure: " + cityModel?.pressure
        tv_sunrise.text = "Sunrise: "  + cityModel?.sunrise
        tv_sunset.text = "Sunset: " + cityModel?.sunset
    }

    companion object{
        private const val ARG_TEXT =""
        fun createBundle(parcel: Parcelable): Bundle {
            val bundle = Bundle()
            bundle.putParcelable(
                ARG_TEXT,
                parcel
            )
            return bundle
        }

    }
}
