package com.averito.firito.ui.shared.app_top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppTopBar(
    title: String,
    navigationIconContent: (@Composable () -> Unit)?,
    actionsContent: (@Composable () -> Unit)?
) {
    Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 3.dp) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                if (navigationIconContent != null) navigationIconContent()
            },
            actions = {
                if (actionsContent != null) actionsContent()
            }
        )
    }
}
