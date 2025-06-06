package com.averito.firito.ui.shared.ui.app_linear_progress_inidicator

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun AppLinearProgressIndicator(
    modifier: Modifier,
    currentValue: Float,
    maxValue: Float,
    color: Color = MaterialTheme.colorScheme.primary
) {
    LinearProgressIndicator(
        progress = { (currentValue / maxValue).coerceIn(0f, 1f) },
        modifier = modifier,
        color = color,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        strokeCap = StrokeCap.Round,
    )
}