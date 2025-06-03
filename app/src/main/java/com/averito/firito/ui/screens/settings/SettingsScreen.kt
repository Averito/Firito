package com.averito.firito.ui.screens.settings

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.settings.components.SettingsScreenContent
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel,
    onActivityGoals: () -> Unit,
    onCaloriesGoals: () -> Unit,
) {
    val context = LocalContext.current

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri ->
        if (uri != null) {
            val success = viewModel.exportToUri(uri)
            Toast.makeText(
                context,
                if (success) "Экспорт выполнен успешно. Перезапустите приложение." else "Ошибка при экспорте",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val success = viewModel.importFromUri(uri)
            Toast.makeText(
                context,
                if (success) "Импорт выполнен. Перезапустите приложение." else "Ошибка при импорте",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.Settings.TITLE)
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(true)
        baseAppLayoutViewModel.setFloatingButton(null)
        baseAppLayoutViewModel.setActions(null)
        baseAppLayoutViewModel.setNavigationIcon(null)
    }

    SettingsScreenContent(
        onActivityGoals = onActivityGoals,
        onCaloriesGoals = onCaloriesGoals,
        onExportClick = {
            exportLauncher.launch("firito_backup.db")
        },
        onImportClick = {
            importLauncher.launch(arrayOf("application/octet-stream"))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreenContent(
        onActivityGoals = {},
        onCaloriesGoals = {},
        onExportClick = {},
        onImportClick = {}
    )
}