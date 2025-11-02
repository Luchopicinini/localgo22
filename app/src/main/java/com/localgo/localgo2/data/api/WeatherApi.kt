package com.localgo.localgo2.data.api

import retrofit2.http.GET

// Respuesta principal
data class WeatherResponse(
    val current_weather: CurrentWeather
)

// Submodelo para los datos actuales
data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val weathercode: Int
)

// Interfaz para Retrofit
interface WeatherApi {
    @GET("v1/forecast?latitude=-33.45&longitude=-70.66&current_weather=true")
    suspend fun getWeather(): WeatherResponse
}
