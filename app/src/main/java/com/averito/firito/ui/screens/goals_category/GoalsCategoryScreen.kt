package com.averito.firito.ui.screens.goals_category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.goals_category.components.GoalsCategoryContent
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes

@Composable
fun GoalsCategoryScreen(
    viewModel: GoalsCategoryViewModel = hiltViewModel(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel,
    category: AppNavGraphRoutes.GoalsCategory.Category,
    back: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    fun saveAndBack() {
        viewModel.onSaveClick()
        back()
    }

    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.GoalsCategory.getTitle(category))
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(false)
        baseAppLayoutViewModel.setFloatingButton(null)
        baseAppLayoutViewModel.setNavigationIcon {
            IconButton(onClick = { saveAndBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }
        baseAppLayoutViewModel.setActions {
            IconButton(onClick = { saveAndBack() }) {
                Icon(Icons.Default.Check, contentDescription = "Сохранить")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadGoals()
    }

    GoalsCategoryContent(
        uiState = uiState,
        category = category,
        onStepsChange = { viewModel.onStepsChange(it) },
        onDistanceChange = { viewModel.onDistanceChange(it) },
        onCaloriesChange = { viewModel.onCaloriesChange(it) },
        onProteinsChange = { viewModel.onProteinsChange(it) },
        onFatsChange = { viewModel.onFatsChange(it) },
        onCarbsChange = { viewModel.onCarbsChange(it) }
    )
}

@Preview(showBackground = true)
@Composable
fun GoalsCategoryScreenPreview() {
    GoalsCategoryContent(
        uiState = GoalsCategoryUiState(),
        category = AppNavGraphRoutes.GoalsCategory.Category.CALORIES,
        onStepsChange = {},
        onDistanceChange = {},
        onCaloriesChange = {},
        onProteinsChange = {},
        onFatsChange = {},
        onCarbsChange = {}
    )
}