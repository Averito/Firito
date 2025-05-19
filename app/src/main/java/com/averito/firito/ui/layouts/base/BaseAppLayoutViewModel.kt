package com.averito.firito.ui.layouts.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BaseAppLayoutViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(BaseAppLayoutUiState("Главная"))
    val uiState get() = _uiState.asStateFlow()

    fun setTitle(title: String): Unit {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun setTopBarVisibility(show: Boolean): Unit {
        _uiState.value = _uiState.value.copy(showTopBar = show)
    }

    fun setBottomBarVisibility(show: Boolean): Unit {
        _uiState.value = _uiState.value.copy(showBottomBar = show)
    }

    fun setFloatingButton(floatingButtonOptions: BaseAppLayoutUiStateFloatingButtonOptions?) {
        _uiState.value = _uiState.value.copy(floatingButtonOptions = floatingButtonOptions)
    }

    fun setNavigationIcon(content: (@Composable () -> Unit)?) {
        _uiState.value = _uiState.value.copy(navigationIcon = content)
    }

    fun setActions(content: (@Composable () -> Unit)?) {
        _uiState.value = _uiState.value.copy(actions = content)
    }
}