package com.localgo.localgo2.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.localgo2.data.UserData
import com.localgo.localgo2.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = UserPreferences(app)

    // Estado actual del usuario en memoria
    private val _userState = MutableStateFlow(
        UserData(
            nombre = "Cargando...",
            email = "Cargando...",
            ubicacion = "Cargando...",
            fotoUri = ""
        )
    )
    val userState: StateFlow<UserData> = _userState

    init {
        // cuando se crea el ViewModel, empezamos a escuchar cambios del DataStore
        viewModelScope.launch {
            prefs.userData.collect { loadedUser ->
                _userState.value = loadedUser
            }
        }
    }

    // Guardar cambios en DataStore
    fun guardarCambios(nuevo: UserData) {
        viewModelScope.launch {
            prefs.saveUserData(nuevo)
        }
    }
}