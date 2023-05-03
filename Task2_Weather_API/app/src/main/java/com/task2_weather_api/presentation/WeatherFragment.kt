package com.task2_weather_api.presentation

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.task2_weather_api.R
import com.task2_weather_api.databinding.FragmentWeatherBinding
import com.task2_weather_api.presentation.viewmodel.WeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        viewModel.setCityModel(arguments?.getParcelable(ARG_TEXT))
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        with(viewModel) {
            cityModel.observe(viewLifecycleOwner) {
                binding.tvName.text = viewModel.cityModel.value?.name
                    ?: throw Exception("WeatherFragmentView tvName got a null from viewModel!")
                binding.tvDeg.text = "Deg: " + viewModel.cityModel.value?.deg
                binding.tvGust.text = "Gust: " + viewModel.cityModel.value?.gust
                binding.tvSpeed.text = "Speed: " + viewModel.cityModel.value?.speed
                binding.tvHumidity.text = "Humidity: " + viewModel.cityModel.value?.humidity
                binding.tvPressure.text = "Pressure: " + viewModel.cityModel.value?.pressure
                binding.tvSunrise.text = "Sunrise: " + viewModel.cityModel.value?.sunrise
                binding.tvSunset.text = "Sunset: " + viewModel.cityModel.value?.sunset
            }
        }
    }

    companion object {
        private const val ARG_TEXT = ""
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
