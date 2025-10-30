package com.localgo.localgo2.data.repositories

import com.localgo.localgo2.data.models.SitioTuristico

class SitioTuristicoRepository {
    private val sitios = mutableListOf(
        SitioTuristico(1, "Playa Blanca", "Arena blanca y mar cristalino"),
        SitioTuristico(2, "Cerro Alegre", "Vista panorámica espectacular")
    )

    fun getSitios(): List<SitioTuristico> = sitios

    fun agregarSitio(sitio: SitioTuristico) {
        sitios.add(sitio)
    }
}
