package com.averito.firito.ui.screens.settings.components

import com.averito.firito.R
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreenContent(
    onExportClick: () -> Unit,
    onImportClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ListItem(
            leadingContent = {Icon(painter = painterResource(id = R.drawable.upload_24px), contentDescription = "Экспорт") },
            headlineContent = { Text("Экспорт базы данных") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onExportClick)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        HorizontalDivider()

        ListItem(
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.download_24px),
                    contentDescription = "Импорт"
                )
            },
            headlineContent = { Text("Импорт базы данных") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onImportClick)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        HorizontalDivider()
    }
}
