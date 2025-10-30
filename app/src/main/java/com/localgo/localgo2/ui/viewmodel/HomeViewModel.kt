package com.localgo.localgo2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.localgo2.data.weather.WeatherRepository
import com.localgo.localgo2.data.weather.WeatherResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val weatherRepo: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    init {
        cargarClimaSantiago()
    }

    private fun cargarClimaSantiago() {
        viewModelScope.launch {
            try {
                val data: WeatherResponse = weatherRepo.getSantiagoWeather()
                _weatherState.value = WeatherUiState.Ok(
                    city = data.name,
                    tempC = data.main.temp.toInt(),
                    feelsLikeC = data.main.feels_like.toInt(),
                    humidity = data.main.humidity,
                    description = data.weather.firstOrNull()?.description ?: "Clima"
                )
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error("No se pudo cargar el clima")
            }
        }
    }
}

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Ok(
        val city: String,
        val tempC: Int,
        val feelsLikeC: Int,
        val humidity: Int,
        val description: String
    ) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
