package com.localgo.localgo2.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.localgo.localgo2.R
import com.localgo.localgo2.ui.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val vm: WeatherViewModel = viewModel()
    val weather = vm.weather
    val isLoading = vm.isLoading
    val error = vm.error

    // Se ejecuta solo una vez al crear la pantalla
    LaunchedEffect(Unit) { vm.loadWeather() }

    val lugares = remember {
        listOf(
            LugarData(
                "Cerro San Cristóbal",
                "Vista panorámica de Santiago. Ideal para fotos al atardecer.",
                R.drawable.cerro_san_cristobal
            ),
            LugarData(
                "Barrio Bellavista",
                "Bares, murales, vida nocturna y cultura bohemia.",
                R.drawable.barrio_bellavista
            ),
            LugarData(
                "Sky Costanera",
                "Mirador 360° más alto de Latinoamérica.",
                R.drawable.sky_costanera
            ),
            LugarData(
                "Museo de Bellas Artes",
                "Arte chileno e internacional en pleno Parque Forestal.",
                R.drawable.museo_bellasartes
            )
        )
    }

    // ✅ LazyColumn principal (solo una raíz scrollable)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 🌤️ Sección de clima
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF00796B))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when {
                        isLoading -> Text("Cargando clima ⛅", color = Color.White)
                        error != null -> Text("Error al cargar clima ❌", color = Color.Red)
                        weather != null -> {
                            val temp = weather.current_weather.temperature
                            val wind = weather.current_weather.windspeed
                            Text("Clima actual en Santiago 🌤️", color = Color.White)
                            Text(
                                "$temp°C — Viento: $wind km/h",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 🏞️ Sección de lugares
        item {
            Text(
                text = "Lugares imperdibles 🏞️",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        items(lugares, key = { it.nombre }) { lugar ->
            LugarCard(lugar)
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun LugarCard(lugar: LugarData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 250.dp, max = 350.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // ✅ Carga de imagen optimizada
            Image(
                painter = painterResource(id = lugar.imagen),
                contentDescription = lugar.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(lugar.nombre, fontWeight = FontWeight.Bold)
                Text(lugar.descripcion, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { /* TODO: abrir en mapa */ },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Ver en mapa →", color = Color(0xFF00796B))
                }
            }
        }
    }
}

data class LugarData(
    val nombre: String,
    val descripcion: String,
    val imagen: Int
)
