package com.averito.firito.ui.shared.ui.app_navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.averito.firito.ui.layouts.base.BaseAppLayout
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.food.FoodScreen
import com.averito.firito.ui.screens.journal.JournalScreen
import com.averito.firito.ui.screens.journal_details.JournalDetailsScreen
import com.averito.firito.ui.screens.main.MainScreen
import com.averito.firito.ui.screens.settings.SettingsScreen
import com.averito.firito.ui.screens.statistics.StatisticsScreen
import com.averito.firito.ui.screens.statistics_category.StatisticsCategoryScreen
import java.time.LocalDate

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel = hiltViewModel()
) {
    BaseAppLayout(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = AppNavGraphRoutes.Main.ROUTE,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            composable(AppNavGraphRoutes.Main.ROUTE) {
                MainScreen(
                    baseAppLayoutViewModel = baseAppLayoutViewModel,
                    toFoodPage = { navController.navigate(AppNavGraphRoutes.MainFood.getRoute(it)) }
                )
            }

            composable(
                route = AppNavGraphRoutes.MainFood.ROUTE,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: -1L

                FoodScreen(
                    foodId = id,
                    baseAppLayoutViewModel = baseAppLayoutViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(AppNavGraphRoutes.Settings.ROUTE) {
                SettingsScreen(baseAppLayoutViewModel = baseAppLayoutViewModel)
            }

            composable(AppNavGraphRoutes.Journal.ROUTE) {
                JournalScreen(
                    baseAppLayoutViewModel = baseAppLayoutViewModel,
                    toDayLogDetails = { navController.navigate(AppNavGraphRoutes.JournalDetails.getRoute(it.toEpochDay())) }
                )
            }

            composable(
                route = AppNavGraphRoutes.JournalDetails.ROUTE,
                arguments = listOf(navArgument("date") { type = NavType.LongType })
            ) { backStackEntry ->
                val dateInMillis = backStackEntry.arguments?.getLong("date") ?: LocalDate.now().toEpochDay()
                val date = LocalDate.ofEpochDay(dateInMillis)

                JournalDetailsScreen(
                    date = date,
                    baseAppLayoutViewModel = baseAppLayoutViewModel,
                    back = { navController.popBackStack() }
                )
            }

            composable(AppNavGraphRoutes.Statistics.ROUTE) {
                StatisticsScreen(
                    baseAppLayoutViewModel = baseAppLayoutViewModel,
                    toStatisticsDetail = { navController.navigate(AppNavGraphRoutes.StatisticsCategory.getRoute(it)) }
                )
            }

            composable(
                route = AppNavGraphRoutes.StatisticsCategory.ROUTE,
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = AppNavGraphRoutes.StatisticsCategory.fromRouteParam(backStackEntry.arguments?.getString("category") ?: "")

                StatisticsCategoryScreen(
                    category = category,
                    baseAppLayoutViewModel = baseAppLayoutViewModel,
                    back = { navController.popBackStack() }
                )
            }
        }
    }
}
