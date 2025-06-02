package com.averito.firito.ui.screens.statistics_category.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.statistics.StatSeries
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties


@Composable
fun <T : Number> StatChartBlock(
    title: String,
    data: List<Line>,
    labels: List<String>,
    stats: List<StatSeries<T>>
) {
    val formatNumber = { n: Number ->
        when (n) {
            is Double -> String.format("%.1f", n)
            is Float -> String.format("%.1f", n)
            else -> n.toString()
        }
    }

    val adjustedLabels = when {
        labels.size > 15 -> labels.mapIndexed { index, label ->
            when {
                index == 0 || index == labels.lastIndex -> label
                index % 2 == 0 -> label
                else -> " "
            }
        }
        else -> labels
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)

        LineChart(
            data = data,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            indicatorProperties = HorizontalIndicatorProperties(
                textStyle = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                padding = 16.dp
            ),
            popupProperties = PopupProperties(
                textStyle = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            ),
            labelHelperProperties = LabelHelperProperties(
                textStyle = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            ),
            labelProperties = LabelProperties(
                enabled = adjustedLabels.isNotEmpty(),
                textStyle = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                labels = adjustedLabels,
                rotation = LabelProperties.Rotation(
                    mode = LabelProperties.Rotation.Mode.Force,
                    degree = 35f
                ),
            )
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            stats.zip(data).forEach { (stat, line) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = line.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text("Мин: ${formatNumber(stat.min)}")
                    Text("Среднее: ${formatNumber(stat.avg)}")
                    Text("Макс: ${formatNumber(stat.max)}")
                }
            }
        }
    }
}
