package com.localgo.localgo2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.localgo.localgo2.ui.home.HomeScreen
import com.localgo.localgo2.ui.map.MapScreen
import com.localgo.localgo2.ui.profile.ProfileScreen
import com.localgo.localgo2.ui.tips.TipsScreen
import com.localgo.localgo2.ui.login.LoginScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login" // login es lo primero que ves
    ) {

        // LOGIN
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // HOME
        composable("home") {
            HomeScreen(navController = navController)
        }

        // MAPA
        composable("map") {
            MapScreen()
        }

        // TIPS
        composable("tips") {
            TipsScreen()
        }

        // PERFIL
        composable("profile") {
            ProfileScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
