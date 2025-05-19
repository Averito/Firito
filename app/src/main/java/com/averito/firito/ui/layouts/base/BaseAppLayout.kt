package com.averito.firito.ui.layouts.base

import com.averito.firito.ui.shared.app_bottom_bar.DefaultAppBottomBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.averito.firito.ui.shared.app_top_bar.DefaultAppTopBar

@Composable
fun BaseAppLayout(
    viewModel: BaseAppLayoutViewModel = hiltViewModel(),
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            if (uiState.showTopBar) {
                DefaultAppTopBar(
                    title = uiState.title,
                    navigationIconContent = uiState.navigationIcon,
                    actionsContent = uiState.actions
                )
            }
        },
        bottomBar = {
            if (uiState.showBottomBar) {
                DefaultAppBottomBar(navController = navController)
            }
        },
        floatingActionButton = {
            if (uiState.floatingButtonOptions != null) {
                FloatingActionButton(
                    onClick = uiState.floatingButtonOptions!!.onClick,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = uiState.floatingButtonOptions!!.imageVector,
                        contentDescription = uiState.floatingButtonOptions!!.contentDescription,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddings ->
        Surface(modifier = Modifier.padding(paddings)) {
            content()
        }
    }
}
