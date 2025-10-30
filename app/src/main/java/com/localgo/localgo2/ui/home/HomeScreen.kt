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
import androidx.navigation.NavController
import com.localgo.localgo2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var climaError by remember { mutableStateOf(false) }

    val lugares = listOf(
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // clima
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF00796B))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (climaError) {
                    Text("No se pudo cargar el clima ☁️", color = Color.White)
                } else {
                    Text("Clima actual en Santiago 🌤️", color = Color.White)
                    Text("23°C, Soleado", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Lugares imperdibles 🏞️",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(lugares) { lugar ->
                LugarCard(lugar)
            }
        }
    }
}

@Composable
fun LugarCard(lugar: LugarData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = lugar.imagen),
                contentDescription = lugar.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 8.dp)
            )
            Text(lugar.nombre, fontWeight = FontWeight.Bold)
            Text(lugar.descripcion, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            TextButton(onClick = { /* TODO: abrir en mapa con navController.navigate("map") */ }) {
                Text("Ver en mapa →", color = Color(0xFF00796B))
            }
        }
    }
}

data class LugarData(
    val nombre: String,
    val descripcion: String,
    val imagen: Int
)
