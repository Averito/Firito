package com.averito.firito.ui.screens.journal_details

import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.data.models.day_log.DayLogModelImpl
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl
import com.averito.firito.data.models.food.FoodModelImpl
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.journal_details.components.JournalDetailsContent
import com.averito.firito.ui.shared.app_navigation.AppNavGraphRoutes
import java.time.LocalDate

@Composable
fun JournalDetailsScreen(
    viewModel: JournalDetailsViewModel = hiltViewModel(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel = hiltViewModel(),
    back: () -> Unit,
    date: LocalDate
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.JournalDetails.title)
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(false)
        baseAppLayoutViewModel.setFloatingButton(null)
        baseAppLayoutViewModel.setNavigationIcon {
            IconButton(onClick = { back() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }
        baseAppLayoutViewModel.setActions(null)
    }

    LaunchedEffect(Unit) {
        viewModel.loadDayLogWithFoods(date)
    }

    LaunchedEffect(uiState.dayLogWithFoods) {
        if (uiState.dayLogWithFoods == null) back()
    }

    JournalDetailsContent(dayLogWithFoods = uiState.dayLogWithFoods!!)
}

@Preview(showBackground = true)
@Composable
fun JournalDetailsScreenPreview() {
    JournalDetailsContent(
        dayLogWithFoods = DayLogWithFoodsModelImpl(
            dayLog = DayLogModelImpl(
                date = LocalDate.now(),
                totalCalories = 625,
                totalProteins = 45f,
                totalFats = 50f,
                totalCarbs = 100f,
                totalSteps = 3420,
                totalDistanceKm = 2.5,
                activeCalories = 1340,
                finalAt = null
            ),
            foods = listOf(
                FoodModelImpl(
                    id = 1,
                    name = "Чипсы",
                    calories = 500,
                    carbs = 100f,
                    fats = 30f,
                    proteins = 4f,
                    weightInGrams = 125,
                    description = null
                ),
                FoodModelImpl(
                    id = 2,
                    name = "Чипсы",
                    calories = 500,
                    carbs = 100f,
                    fats = 30f,
                    proteins = 4f,
                    weightInGrams = 125,
                    description = null
                ),
                FoodModelImpl(
                    id = 3,
                    name = "Чипсы",
                    calories = 500,
                    carbs = 100f,
                    fats = 30f,
                    proteins = 4f,
                    weightInGrams = 125,
                    description = null
                )
            )
        )
    )
}
