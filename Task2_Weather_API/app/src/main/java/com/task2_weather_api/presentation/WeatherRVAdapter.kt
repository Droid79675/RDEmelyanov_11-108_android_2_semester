package com.task2_weather_api.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.task2_weather_api.R
import com.task2_weather_api.databinding.ItemWeatherBinding
import com.task2_weather_api.domain.model.CityModel


class WeatherRVAdapter(private val onItemClick: (CityModel) -> Unit) :
    RecyclerView.Adapter<WeatherRVAdapter.RVViewHolder>() {
    private var cityList = emptyList<CityModel>()
    private lateinit var binding: ItemWeatherBinding
//    class RVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }

    inner class RVViewHolder(val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        with(holder) {
            with(cityList[position]) {
                binding.tvName.text = name
                val icon: String? = icon
                binding.ivCover.load("https://openweathermap.org/img/w/$icon.png")
                binding.tvTemperature.text = "Temp:$temp C"
                when (temp?.toDouble()?.toInt()) {
                    in 25..50 -> binding.tvName.setTextColor(Color.RED).also {
                        binding.tvName.setTextColor(Color.RED)
                    }
                    in 15..24 -> binding.tvName.setTextColor(Color.YELLOW).also {
                        binding.tvName.setTextColor(Color.YELLOW)
                    }
                    in 0..14 -> binding.tvName.setTextColor(Color.GREEN).also {
                        binding.tvName.setTextColor(Color.GREEN)
                    }
                    in -15..-1 -> binding.tvName.setTextColor(Color.CYAN).also {
                        binding.tvName.setTextColor(Color.CYAN)
                    }
                    in -40..-16 -> binding.tvName.setTextColor(Color.BLUE).also {
                        binding.tvName.setTextColor(Color.BLUE)
                    }
                }

                holder.itemView.setOnClickListener {
                    binding.btnItem.findNavController().navigate(
                        R.id.action_fragmentMainWeather_to_weatherFragment,
                        WeatherFragment.createBundle(cityList[position])
                    )
                }
            }
        }
//        val currentItem = cityList[position]
//        holder.itemView.tv_name.text = currentItem.name
//        val icon: String? = currentItem.icon
//        holder.itemView.iv_cover.load("https://openweathermap.org/img/w/$icon.png")
//        val temporary = "Temp:" + currentItem.temp + " C"
//        holder.itemView.tv_temperature.text = temporary
//        }
//        holder.itemView.item_weather.setOnClickListener {
//            onItemClick(currentItem)
//
//            holder.itemView.btn_item.findNavController().navigate(R.id.action_fragmentMainWeather_to_weatherFragment,
//                WeatherFragment.createBundle(currentItem)
//            )
//            }
//        }

    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    fun setData(cities: MutableList<CityModel>) {
        this.cityList = cities
        notifyDataSetChanged()
    }
}
