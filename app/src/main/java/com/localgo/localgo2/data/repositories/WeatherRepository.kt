package com.localgo.localgo2.data.repositories

import com.localgo.localgo2.data.api.WeatherApi
import com.localgo.localgo2.data.api.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(WeatherApi::class.java)

    suspend fun getWeather(): WeatherResponse {
        return api.getWeather()
    }
}
