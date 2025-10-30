package com.localgo.localgo2.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Inicio")
    object Map : BottomNavItem("map", Icons.Filled.Place, "Mapa")
    object Tips : BottomNavItem("tips", Icons.Filled.TipsAndUpdates, "Tips")
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "Perfil")
}
