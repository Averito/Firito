package com.averito.firito.ui.shared.ui.app_bottom_bar

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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.averito.firito.R
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes

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
            selected = currentRoute == AppNavGraphRoutes.Main.ROUTE,
            onClick = { onClickNavigationBarItem(AppNavGraphRoutes.Main.ROUTE) },
            icon = { Icon(Icons.Default.Home, contentDescription = AppNavGraphRoutes.Main.TITLE) },
            label = { Text(AppNavGraphRoutes.Main.TITLE) }
        )
        NavigationBarItem(
            selected = currentRoute == AppNavGraphRoutes.Journal.ROUTE,
            onClick = { onClickNavigationBarItem(AppNavGraphRoutes.Journal.ROUTE) },
            icon = { Icon(Icons.Default.DateRange, contentDescription = AppNavGraphRoutes.Journal.TITLE) },
            label = { Text(AppNavGraphRoutes.Journal.TITLE) }
        )
        NavigationBarItem(
            selected = currentRoute == AppNavGraphRoutes.Statistics.ROUTE,
            onClick = { onClickNavigationBarItem(AppNavGraphRoutes.Statistics.ROUTE) },
            icon = {
                Icon(painter = painterResource(id = R.drawable.monitoring_24px), contentDescription = AppNavGraphRoutes.Statistics.TITLE)
            },
            label = { Text(AppNavGraphRoutes.Statistics.TITLE) }
        )
        NavigationBarItem(
            selected = currentRoute == AppNavGraphRoutes.Settings.ROUTE,
            onClick = { onClickNavigationBarItem(AppNavGraphRoutes.Settings.ROUTE) },
            icon = { Icon(Icons.Default.Settings, contentDescription = AppNavGraphRoutes.Settings.TITLE) },
            label = { Text(AppNavGraphRoutes.Settings.TITLE) }
        )
    }
}
