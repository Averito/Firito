package com.averito.firito.ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.averito.firito.ui.shared.ui.app_linear_progress_inidicator.AppLinearProgressIndicator

@Composable
fun MacroItem(name: String, value: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(name)
            Text("${"%.1f".format(value)} г")
        }
        AppLinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            currentValue = value,
            maxValue = 100f
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MacroItemPreview() {
    Column {
        MacroItem("Белки", 20f)
        MacroItem("Жиры", 15f)
        MacroItem("Углеводы", 32f)
    }
}
