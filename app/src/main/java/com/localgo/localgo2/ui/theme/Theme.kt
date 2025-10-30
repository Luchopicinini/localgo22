package com.localgo.localgo2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta tipo travel app / verde turquesa
private val LightColors = lightColorScheme(
    primary = Color(0xFF006A61),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF71F4DA),
    onPrimaryContainer = Color(0xFF00201C),

    secondary = Color(0xFF4C6360),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCFE9E4),
    onSecondaryContainer = Color(0xFF081F1D),

    background = Color(0xFFF8FAF9),
    onBackground = Color(0xFF1A1C1C),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1C),
    onSurfaceVariant = Color(0xFF5B6B69),

    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002),
)

@Composable
fun LocalGo2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
