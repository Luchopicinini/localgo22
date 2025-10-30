package com.localgo.localgo2.data.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(
    private val apiKey: String
) {

    private val api: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/") // base
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    // Santiago centro: -33.4489, -70.6693
    suspend fun getSantiagoWeather(): WeatherResponse {
        return api.getWeather(
            lat = -33.4489,
            lon = -70.6693,
            apiKey = apiKey
        )
    }
}
