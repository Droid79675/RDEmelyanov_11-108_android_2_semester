package com.example.task1_weather_api.ui

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task1_weather_api.R
import com.example.task1_weather_api.data.response.City
import kotlinx.android.synthetic.main.item_weather.view.*
import android.os.Bundle
import com.example.task1_weather_api.model.CityModel


class RVAdapter(private val onItemClick: (City) -> Unit) : RecyclerView.Adapter<RVAdapter.RVViewHolder>() {
    private var cityList = emptyList<City>()
    class RVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        return RVViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val currentItem = cityList[position]
        holder.itemView.tv_name.text = currentItem.name
        val icon: String = currentItem.weather.firstOrNull()!!.icon
        holder.itemView.iv_cover.load("https://openweathermap.org/img/w/$icon.png")
        val temporary = "Temp:" + currentItem.main.temp.toString() + " C"
        holder.itemView.tv_temperature.text = temporary
        when(currentItem.main.temp.toInt()){
            in 25..50 -> holder.itemView.tv_name.setTextColor(Color.RED).also {
                holder.itemView.tv_temperature.setTextColor(Color.RED)
            }
            in 15..24 -> holder.itemView.tv_name.setTextColor(Color.YELLOW).also {
                    holder.itemView.tv_temperature.setTextColor(Color.YELLOW)
            }
            in 0..14 -> holder.itemView.tv_name.setTextColor(Color.GREEN).also {
                holder.itemView.tv_temperature.setTextColor(Color.GREEN)
            }
            in -15..-1 -> holder.itemView.tv_name.setTextColor(Color.CYAN).also {
                holder.itemView.tv_temperature.setTextColor(Color.CYAN)
            }
            in -40..-16 -> holder.itemView.tv_name.setTextColor(Color.BLUE).also {
                holder.itemView.tv_temperature.setTextColor(Color.BLUE)
            }
        }
        holder.itemView.item_weather.setOnClickListener {
            val city: CityModel = CityModel(currentItem.name,
            currentItem.wind.deg.toString(),
            currentItem.wind.gust.toString(),
            currentItem.wind.speed.toString(),
            currentItem.main.humidity.toString(),
            currentItem.main.pressure.toString(),
            currentItem.sys.sunrise.toString(),
            currentItem.sys.sunset.toString())

            onItemClick(currentItem)

            holder.itemView.btn_item.findNavController().navigate(R.id.action_fragmentMainWeather_to_weatherFragment,
            WeatherFragment.createBundle(city))
            Log.d("DEG", currentItem.wind.deg.toString())
            }
        }

    override fun getItemCount(): Int {
        return cityList.size
    }

    fun setData(cities: MutableList<City>) {
        this.cityList = cities
        notifyDataSetChanged()
    }


}
