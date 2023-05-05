package com.task2_weather_api.presentation

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.task2_weather_api.R
import com.task2_weather_api.databinding.FragmentMainWeatherBinding
import com.task2_weather_api.di.DataContainer
import com.task2_weather_api.domain.model.CityModel
import com.task2_weather_api.presentation.viewmodel.MainViewModel
import com.task2_weather_api.utils.ContextProvider
import com.task2_weather_api.utils.showSnackbar
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main_weather) {

    private var _binding: FragmentMainWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ContextProvider.setApplication(context?.applicationContext as Application)
        MainViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentMainWeatherBinding.inflate(inflater, container, false)
        val view = binding.root

        val weatherRVAdapter = WeatherRVAdapter()
        {
        }

        val recyclerView = binding.rvWeather
        recyclerView.adapter = weatherRVAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeViewModel()
        with(binding) {
            btnLoad.setOnClickListener {
                viewModel.onLoadClick(etCity.text.toString(), weatherRVAdapter)
            }

            etCity.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.onLoadClick(etCity.text.toString(), weatherRVAdapter)
                }
                true
            }
        }
        return view
    }

    private fun loadRV(cities: MutableList<CityModel>?, adapter: WeatherRVAdapter) {
        Log.d("MAINFRAGMENT", "LOADRV")
        if (cities != null) {
            adapter.setData(cities)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                binding.progress.isVisible = it
            }
            weatherModel.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                showTemp(it.temperature)
                showWeatherIcon(it.icon)
            }
            error.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                showError(it)
            }
            combinedCityListWeatherRVAdapter.observe(viewLifecycleOwner) {
                Log.d("MAINFRAGMENT", "ЗАШЁЛ")
                if (it == null) return@observe
                it.second?.let { it2 -> loadRV(it.first, it2) }
            }
        }
    }


//    private fun onLoadClick(adapter: RVAdapter, view: View) {
//        view.et_city?.hideKeyboard()
//        val query = view.et_city?.text.toString()
//        loadWeather(query, adapter, view)
//    }

//    private fun loadWeather(query: String, adapter: RVAdapter, view: View) {
//        lifecycleScope.launch {
//            try {
//                showLoading(true, view)
//                api.getWeather(query).also {
//                    showTemp(it.main.temp, view)
//                    it.weather.firstOrNull()?.also {
//                        showWeatherIcon(it.icon, view)
//                    }
//                }
//
//                val string: String = getLatitudeAndLongitude()
//                val wayLongitude =
//                    string.substringBefore(" ").toDouble()//я без поянтия почему возвращается 0.0
//                val wayLatitude =
//                    string.substringAfter(" ").toDouble()//я без поянтия почему возвращается 0.0
//                //к тому же смена gps точки не помогла и всё равно возвращается адрес офиса гугла
//                val locality = api.getCities("55.830433", "49.066082", 11).list as MutableList<City>
//                for(local in locality){
//                    Log.d("LOCAL", local.name)
//                }
//                cityRepositoryImpl.setCities(locality)
//                loadRV(adapter, getNearbyAddresses(query))
//            } catch (error: Throwable) {
//                showError(error, view)
//            } finally {
//                showLoading(false, view)
//            }
//        }
//    }

    private fun showError(error: Throwable) {
        requireActivity().findViewById<View>(android.R.id.content)
            ?.showSnackbar(error.message ?: "Error")
    }

    private fun showTemp(temp: Double) {
        binding.tvHometownTemp.run {
            text = "Temp: $temp C"
            isVisible = true
        }
    }

    private fun showWeatherIcon(id: String) {
        Timber.e(id)
        binding.ivIcon.load("https://openweathermap.org/img/w/$id.png") {
            crossfade(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
