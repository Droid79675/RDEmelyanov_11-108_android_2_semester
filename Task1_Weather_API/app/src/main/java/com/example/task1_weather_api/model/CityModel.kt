package com.example.task1_weather_api.model

import android.os.Parcel
import android.os.Parcelable

data class CityModel (val name: String?,
                      val deg: String?,
                      val gust: String?,
                      val speed: String?,
                      val humidity: String?,
                      val pressure: String?,
                      val sunrise: String?,
                      val sunset: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
        )


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(deg)
        parcel.writeString(gust)
        parcel.writeString(speed)
        parcel.writeString(humidity)
        parcel.writeString(pressure)
        parcel.writeString(sunrise)
        parcel.writeString(sunset)
    }

    companion object CREATOR : Parcelable.Creator<CityModel> {
        override fun createFromParcel(parcel: Parcel): CityModel {
            return CityModel(parcel)
        }

        override fun newArray(size: Int): Array<CityModel?> {
            return arrayOfNulls(size)
        }
    }

}
