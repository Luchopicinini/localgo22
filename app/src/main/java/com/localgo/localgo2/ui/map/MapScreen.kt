package com.localgo.localgo2.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import com.google.accompanist.permissions.isGranted
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn


data class Lugar(
    val nombre: String,
    val descripcion: String,
    val region: String,
    val latLng: LatLng
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-33.45, -70.66), 4.8f)
    }

    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var lugarSeleccionado by remember { mutableStateOf<Lugar?>(null) }

    val scope = rememberCoroutineScope()

    // 1️⃣ Solicita permiso con diálogo personalizado
    when (locationPermission.status) {
        is PermissionStatus.Denied -> {
            PermissionRequestDialog(
                onConfirm = { locationPermission.launchPermissionRequest() }
            )
        }
        else -> {}
    }

    // 2️⃣ Obtiene la ubicación si el permiso fue concedido
    LaunchedEffect(locationPermission.status) {
        if (locationPermission.status.isGranted) {
            scope.launch {
                val loc = getLastKnownLocationOrNull(context)
                if (loc != null) {
                    val latLng = LatLng(loc.latitude, loc.longitude)
                    userLocation = latLng
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 13f)
                }
            }
        }
    }

    // 3️⃣ Lista de lugares turísticos de Chile
    val lugares = remember {
        listOf(
            Lugar("Desierto de Atacama", "El más árido del mundo, ideal para astroturismo", "Antofagasta", LatLng(-23.65, -68.22)),
            Lugar("Valle de la Luna", "Formaciones rocosas únicas", "Antofagasta", LatLng(-22.92, -68.21)),
            Lugar("La Portada", "Arco natural sobre el mar", "Antofagasta", LatLng(-23.585, -70.391)),
            Lugar("Bahía Inglesa", "Playas blancas y aguas turquesas", "Atacama", LatLng(-27.095, -70.833)),
            Lugar("Valle del Elqui", "Cielos despejados y viñedos", "Coquimbo", LatLng(-29.92, -70.01)),
            Lugar("La Serena", "Playas y arquitectura colonial", "Coquimbo", LatLng(-29.9, -71.25)),
            Lugar("Valparaíso", "Puerto histórico y colorido", "Valparaíso", LatLng(-33.047, -71.612)),
            Lugar("Viña del Mar", "Balneario elegante", "Valparaíso", LatLng(-33.024, -71.551)),
            Lugar("Santiago", "Capital moderna con historia", "Metropolitana", LatLng(-33.45, -70.66)),
            Lugar("Cerro San Cristóbal", "Mirador icónico con santuario", "Santiago", LatLng(-33.4145, -70.6413)),
            Lugar("Cajón del Maipo", "Montañas y naturaleza", "Santiago", LatLng(-33.619, -70.351)),
            Lugar("Rancagua", "Historia de la independencia", "O’Higgins", LatLng(-34.17, -70.74)),
            Lugar("Pichilemu", "Capital del surf chileno", "O’Higgins", LatLng(-34.386, -72.004)),
            Lugar("Talca", "Puerta al Maule cordillerano", "Maule", LatLng(-35.43, -71.66)),
            Lugar("Constitución", "Playas y formaciones rocosas", "Maule", LatLng(-35.332, -72.416)),
            Lugar("Concepción", "Cultura, música y universidades", "Biobío", LatLng(-36.82, -73.05)),
            Lugar("Lota", "Exmina de carbón y costa", "Biobío", LatLng(-37.09, -73.15)),
            Lugar("Temuco", "Corazón de la Araucanía", "Araucanía", LatLng(-38.74, -72.59)),
            Lugar("Pucón", "Aventura, termas y volcanes", "Araucanía", LatLng(-39.28, -71.97)),
            Lugar("Valdivia", "Ríos, cerveza y bosques", "Los Ríos", LatLng(-39.81, -73.24)),
            Lugar("Puerto Varas", "Lago y volcán Osorno", "Los Lagos", LatLng(-41.32, -72.98)),
            Lugar("Frutillar", "Teatro del Lago y vistas alpinas", "Los Lagos", LatLng(-41.13, -73.06)),
            Lugar("Castro", "Palafitos y cultura chilota", "Chiloé", LatLng(-42.48, -73.76)),
            Lugar("Coyhaique", "Puerta de la Carretera Austral", "Aysén", LatLng(-45.57, -72.07)),
            Lugar("Capillas de Mármol", "Formaciones sobre el Lago General Carrera", "Aysén", LatLng(-46.65, -72.63)),
            Lugar("Torres del Paine", "Montañas icónicas del mundo", "Magallanes", LatLng(-51.25, -72.33)),
            Lugar("Punta Arenas", "Ciudad más austral de Chile continental", "Magallanes", LatLng(-53.16, -70.91))
        )
    }

    // 4️⃣ Mapa
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapType = MapType.NORMAL),
            uiSettings = MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = true)
        ) {
            lugares.forEach { lugar ->
                Marker(
                    state = MarkerState(position = lugar.latLng),
                    title = lugar.nombre,
                    snippet = "${lugar.region} · ${lugar.descripcion}",
                    onClick = {
                        lugarSeleccionado = lugar
                        false
                    }
                )
            }

            userLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Estás aquí",
                    snippet = "Tu ubicación actual"
                )
            }
        }

        // 5️⃣ Tarjeta informativa del lugar seleccionado
        lugarSeleccionado?.let { lugar ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(lugar.nombre, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text(lugar.region, style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(4.dp))
                    Text(lugar.descripcion, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(4.dp))
                    Text("📍 ${lugar.latLng.latitude}, ${lugar.latLng.longitude}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
private suspend fun getLastKnownLocationOrNull(context: Context): Location? {
    return try {
        val client = LocationServices.getFusedLocationProviderClient(context)
        suspendCancellableCoroutine { cont ->
            client.lastLocation
                .addOnSuccessListener { loc -> cont.resume(loc, onCancellation = null) }
                .addOnFailureListener { cont.resume(null, onCancellation = null) }
        }
    } catch (e: Exception) {
        null
    }
}

@Composable
fun PermissionRequestDialog(onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Permitir acceso")
            }
        },
        title = { Text("Permiso de ubicación requerido") },
        text = {
            Text(
                "Para mostrarte tu posición en el mapa y los lugares cercanos, necesitamos acceder a tu ubicación. " +
                        "Puedes permitirlo ahora para mejorar tu experiencia."
            )
        },
        icon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
    )
}
