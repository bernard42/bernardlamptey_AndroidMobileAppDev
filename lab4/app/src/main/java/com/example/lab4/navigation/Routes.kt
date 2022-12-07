package com.example.lab4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lab4.composable.Home
import com.example.lab4.composable.Profile
import com.example.lab4.composable.Settings

@Composable
fun Routes(navController: NavHostController)
{
    NavHost(
        navController = navController,
        startDestination = BottomBarItems.Home.route
    ) {
        composable(route = BottomBarItems.Home.route) {
            Home()
        }
        composable(route = BottomBarItems.Profile.route) {
            Profile()
        }
        composable(route = BottomBarItems.Settings.route) {
            Settings()
        }
    }
}
