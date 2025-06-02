package com.averito.firito.ui.screens.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.averito.firito.ui.shared.ui.app_linear_progress_inidicator.AppLinearProgressIndicator

@Composable
fun MacroItem(name: String, value: Float, maxValue: Float) {
    val exceeded = value > maxValue
    val color = if (exceeded) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    Column(modifier = Modifier.fillMaxWidth()) {
        if (maxValue > 0f) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(name, color = color)
                Text("${"%.1f".format(value)} / ${"%.1f".format(maxValue)} г", color = color)
            }

            AppLinearProgressIndicator(
                currentValue = value,
                maxValue = maxValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                color = if (exceeded) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(name)
                Text("${"%.1f".format(value)} г")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MacroItemPreview() {
    Column(Modifier.padding(16.dp)) {
        MacroItem("Белки", 80f, 100f)
        MacroItem("Жиры", 150f, 130f)
        MacroItem("Углеводы", 300f, 300f)
        MacroItem("Углеводы2", 300f, 0f)
    }
}
