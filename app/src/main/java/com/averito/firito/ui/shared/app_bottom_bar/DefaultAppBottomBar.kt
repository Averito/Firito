package com.averito.firito.ui.shared.app_bottom_bar

import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.averito.firito.ui.shared.app_navigation.AppNavGraphRoutes

@Composable
fun DefaultAppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun onClickNavigationBarItem(route: String) {
        if (currentRoute == route) return
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == AppNavGraphRoutes.Main.route,
            onClick = { onClickNavigationBarItem(AppNavGraphRoutes.Main.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = AppNavGraphRoutes.Main.title) },
            label = { Text(AppNavGraphRoutes.Main.title) }
        )
        NavigationBarItem(
            selected = currentRoute == AppNavGraphRoutes.Journal.route,
            onClick = { onClickNavigationBarItem(AppNavGraphRoutes.Journal.route) },
            icon = { Icon(Icons.Default.DateRange, contentDescription = AppNavGraphRoutes.Journal.title) },
            label = { Text(AppNavGraphRoutes.Journal.title) }
        )
        NavigationBarItem(
            selected = currentRoute == AppNavGraphRoutes.Settings.route,
            onClick = { onClickNavigationBarItem(AppNavGraphRoutes.Settings.route) },
            icon = { Icon(Icons.Default.Settings, contentDescription = AppNavGraphRoutes.Settings.title) },
            label = { Text(AppNavGraphRoutes.Settings.title) }
        )
    }
}
