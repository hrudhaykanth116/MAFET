package com.hrudhaykanth116.training.customlayout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Composable
fun SleepTracker() {

    val hours = listOf<String>(
        "1",
        "2",
        "3",
        "4",
        "5",
    )

    TimeGraph(
        hoursHeader = {
            Row() {
                hours.forEach { Text(text = it, modifier = Modifier.width(50.dp)) }
            }
        },
        rowCount = 7,
        dayLabel = {
            Column() {
                hours.forEach { Text(text = it, modifier = Modifier.width(50.dp)) }
            }
        },
        sleepBar = {

        }
    )
}

@Composable
fun TimeGraph(
    hoursHeader: @Composable () -> Unit,
    rowCount: Int,
    dayLabel: @Composable (index: Int) -> Unit,
    sleepBar: @Composable (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {


    val dayLabels: @Composable () -> Unit = @Composable {
        repeat(rowCount) { index ->
            dayLabel(index)
        }
    }

    val sleepBars = @Composable {
        repeat(rowCount) { index ->
            dayLabel(index)
        }
    }

    // Layout(
    //     contents = listOf(hoursHeader, dayLabels, sleepBars),
    //     modifier = modifier,
    // ){ (hoursHeaderMeasurables, dayLabelsMeasurables, sleepBarsMeasurables), constraints: Constraints ->
    //
    //     val hoursHeaderPlaceable = hoursHeaderMeasurables.first().measure(constraints)
    //
    // }

}