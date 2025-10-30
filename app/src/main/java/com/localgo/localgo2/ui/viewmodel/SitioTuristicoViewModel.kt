package com.localgo.localgo2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.localgo2.data.models.SitioTuristico
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SitioTuristicoViewModel : ViewModel() {

    private val _sitios = MutableStateFlow<List<SitioTuristico>>(emptyList())
    val sitios: StateFlow<List<SitioTuristico>> = _sitios

    init {
        cargarSitios()
    }

    private fun cargarSitios() {
        viewModelScope.launch {
            _sitios.value = listOf(
                SitioTuristico(1, "Playa Blanca", "Arena blanca y mar cristalino"),
                SitioTuristico(2, "Cerro Alegre", "Vista panorámica espectacular")
            )
        }
    }
}
