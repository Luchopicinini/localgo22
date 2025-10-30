package com.localgo.localgo2.ui.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.localgo.localgo2.navigation.AppNavGraph
import com.localgo.localgo2.navigation.BottomNavBar

@Composable
fun RootScaffold() {
    // controlador de navegación global
    val navController = rememberNavController()

    // ruta actual (para ocultar barra en login)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // mostramos barra nav SOLO si no estamos en login
            if (currentRoute != "login") {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(innerPadding)
        ) {
            AppNavGraph(navController = navController)
        }
    }
}
