package com.averito.firito.ui.layouts.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BaseAppLayoutUiState(
    val title: String,
    val showBottomBar: Boolean = true,
    val showTopBar: Boolean = true,
    val floatingButtonOptions: BaseAppLayoutUiStateFloatingButtonOptions? = null,
    val navigationIcon: (@Composable () -> Unit)? = null,
    val actions: (@Composable () -> Unit)? = null
)

data class BaseAppLayoutUiStateFloatingButtonOptions(
    val onClick: () -> Unit,
    val imageVector: ImageVector,
    val contentDescription: String,
)