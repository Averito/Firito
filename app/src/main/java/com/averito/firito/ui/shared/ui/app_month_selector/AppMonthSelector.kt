package com.averito.firito.ui.shared.ui.app_month_selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AppMonthSelector(
    selectedDate: YearMonth,
    updateDate: (YearMonth) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { updateDate(selectedDate.minusMonths(1)) }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Предыдущий месяц")
        }

        Text(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = selectedDate.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()).replaceFirstChar { it.uppercaseChar() },
            style = MaterialTheme.typography.titleMedium
        )

        IconButton(onClick = { updateDate(selectedDate.plusMonths(1)) }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Следующий месяц")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppMonthSelectorPreview() {
    var selectedDate by remember { mutableStateOf(YearMonth.now()) }

    fun updateDate(date: YearMonth) {
        selectedDate = date
    }

    AppMonthSelector(
        selectedDate = selectedDate,
        updateDate = { updateDate(it) }
    )
}