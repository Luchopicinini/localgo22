package com.localgo.localgo2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.localgo2.data.repositories.WeatherRepository
import com.localgo.localgo2.data.api.WeatherResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class WeatherViewModel : ViewModel() {
    private val repo = WeatherRepository()
    var weather by mutableStateOf<WeatherResponse?>(null)
    var isLoading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)

    fun loadWeather() {
        viewModelScope.launch {
            try {
                isLoading = true
                weather = repo.getWeather()
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Error desconocido"
            } finally {
                isLoading = false
            }
        }
    }
}
