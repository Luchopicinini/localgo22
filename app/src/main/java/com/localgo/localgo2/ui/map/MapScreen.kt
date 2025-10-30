package com.localgo.localgo2.ui.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@Composable
fun MapScreen() {
    // Coordenadas base: Santiago de Chile
    val santiago = LatLng(-33.4489, -70.6693)

    // Estado de la cámara
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(santiago, 13f)
    }

    // Lista de lugares con coordenadas reales
    val lugares = listOf(
        Lugar(
            "Cerro San Cristóbal",
            LatLng(-33.4145, -70.6413),
            "Mirador panorámico y Virgen de la Inmaculada"
        ),
        Lugar(
            "Barrio Bellavista",
            LatLng(-33.4332, -70.6343),
            "Murales, bares, cultura y bohemia"
        ),
        Lugar(
            "Sky Costanera",
            LatLng(-33.4177, -70.6065),
            "Mirador 360° en Costanera Center"
        ),
        Lugar(
            "Museo Bellas Artes",
            LatLng(-33.4369, -70.6414),
            "Arte clásico y contemporáneo en el Parque Forestal"
        ),
        Lugar(
            "Parque Bicentenario",
            LatLng(-33.4033, -70.6067),
            "Áreas verdes, laguna y paseos al aire libre"
        )
    )

    // Lugar seleccionado (cuando toques un marcador)
    var lugarSeleccionado by remember { mutableStateOf<Lugar?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 🗺️ Mapa visible
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapType = MapType.NORMAL),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                compassEnabled = true,
                myLocationButtonEnabled = false
            )
        ) {
            lugares.forEach { lugar ->
                Marker(
                    state = MarkerState(position = lugar.latLng),
                    title = lugar.nombre,
                    snippet = lugar.descripcion,
                    onClick = {
                        lugarSeleccionado = lugar
                        false // deja mostrar el InfoWindow
                    }
                )
            }
        }

        // 📍 Tarjeta inferior al seleccionar un marcador
        lugarSeleccionado?.let { lugar ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        lugar.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        lugar.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "📍 ${lugar.latLng.latitude}, ${lugar.latLng.longitude}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

data class Lugar(
    val nombre: String,
    val latLng: LatLng,
    val descripcion: String
)
