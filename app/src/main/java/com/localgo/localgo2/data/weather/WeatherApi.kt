package com.localgo.localgo2.data.weather

import retrofit2.http.GET
import retrofit2.http.Query

// Respuesta mínima que nos interesa del API
data class WeatherResponse(
    val main: MainInfo,
    val weather: List<WeatherInfo>,
    val name: String
)

data class MainInfo(
    val temp: Float,
    val feels_like: Float,
    val humidity: Int
)

data class WeatherInfo(
    val description: String,
    val icon: String
)

interface WeatherApi {
    // Ejemplo endpoint:
    // api.openweathermap.org/data/2.5/weather?lat=-33.4489&lon=-70.6693&units=metric&appid=API_KEY&lang=es
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "es",
        @Query("appid") apiKey: String
    ): WeatherResponse
}
