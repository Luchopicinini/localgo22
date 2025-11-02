package com.localgo.localgo2.ui.tips

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TipsScreen() {
    val tips = listOf(
        " Sube al Cerro San Cristóbal al atardecer para la mejor vista de Santiago.",
        " Recorre el Barrio Lastarria, lleno de arte, cafés y librerías.",
        " Almorzá en el Mercado Central, ideal para probar mariscos frescos.",
        " Usa bicicleta para moverte por Providencia y Vitacura, hay ciclovías seguras.",
        " Visita el GAM, siempre hay exposiciones o espectáculos gratuitos.",
        " Tomate un café en el barrio Italia, cada rincón tiene su estilo.",
        " Camina por el Parque Bicentenario, perfecto para un picnic o ver aves.",
        "️ Entra al Museo de Bellas Artes, es gratis y tiene arquitectura hermosa.",
        " Prueba un completo en Fuente Alemana, un clásico santiaguino.",
        " Sube al Sky Costanera, el mirador más alto de Latinoamérica.",
        " Usa el Metro en hora valle (no punta), es más tranquilo y rápido.",
        " Si te gusta el vino, hacé una cata en el Valle del Maipo (a 40 min).",
        " En verano, busca los conciertos gratuitos del Parque de las Esculturas.",
        " Sube el Cerro Manquehue temprano; la vista vale el esfuerzo.",
        "️ Visita ferias locales como la de Ñuñoa o la Persa BioBío, llenas de historia.",
        " Desde el puente Pío Nono, tenés una vista genial del río Mapocho.",
        "️ En verano, usá protector solar y llevá agua; el calor puede ser fuerte.",
        " El agua del grifo es potable, no hace falta comprar botellas.",
        " Usa RedBus o Bip! para pagar transporte, la tarjeta sirve en todo.",
        " No camines solo por zonas solitarias de noche, especialmente en el centro."
    )

    // Fondo degradado suave
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFE3F2FD),
                        Color(0xFFBBDEFB),
                        Color(0xFFE1F5FE)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tips locales",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0D47A1),
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )

            Text(
                text = "Consejos útiles, curiosidades y secretos para disfrutar la ciudad como un local.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF1A237E),
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = 20.sp
            )

            // Lista animada de tips
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 40.dp)
            ) {
                itemsIndexed(tips) { index, tip ->
                    var visible by remember { mutableStateOf(false) }

                    // Activar animación con retardo progresivo
                    LaunchedEffect(Unit) {
                        visible = true
                    }

                    AnimatedVisibility(
                        visible = visible,
                        enter = slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(durationMillis = 300 + index * 40)
                        ) + fadeIn(animationSpec = tween(300 + index * 50))
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFFFFF)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = tip,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        lineHeight = 22.sp,
                                        color = Color(0xFF0D47A1),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
