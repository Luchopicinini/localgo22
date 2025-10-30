package com.localgo.localgo2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.localgo2.data.models.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    init {
        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        viewModelScope.launch {
            _usuarios.value = listOf(
                Usuario(1, "Luciano", "luciano@mail.com"),
                Usuario(2, "Ana", "ana@mail.com")
            )
        }
    }
}
