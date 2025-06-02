package com.averito.firito.ui.screens.statistics_category.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.statistics.StatSeries
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.*

@Composable
fun <T : Number> StatChartBlock(
    title: String,
    data: List<Line>,
    labels: List<String>,
    stats: List<StatSeries<T>>,
    diffs: List<StatSeries<Int>> = emptyList()
) {
    val formatNumber = { n: Number ->
        when (n) {
            is Double -> String.format("%.1f", n)
            is Float -> String.format("%.1f", n)
            else -> n.toString()
        }
    }

    val formatDiff = { n: Number ->
        val value = when (n) {
            is Double -> String.format("%.1f", n)
            is Float -> String.format("%.1f", n)
            else -> n.toString()
        }
        val prefix = if (n.toDouble() > 0) "+" else ""
        "$prefix$value%"
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

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            stats.zip(data).forEachIndexed { index, (stat, line) ->
                val diff = diffs.getOrNull(index)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = line.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Мин: ${formatNumber(stat.min)}")
                        Text("Среднее: ${formatNumber(stat.avg)}")
                        Text("Макс: ${formatNumber(stat.max)}")
                    }

                    diff?.let {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (it.min != 0) Text("(${formatDiff(it.min)})", style = MaterialTheme.typography.labelSmall)
                            if (it.avg != 0) Text("(${formatDiff(it.avg)})", style = MaterialTheme.typography.labelSmall)
                            if (it.max != 0) Text("(${formatDiff(it.max)})", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

    }
}
