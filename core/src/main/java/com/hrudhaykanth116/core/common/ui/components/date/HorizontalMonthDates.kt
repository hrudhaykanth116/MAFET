package com.hrudhaykanth116.core.common.ui.components.date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.div
import kotlin.math.abs
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private const val EACH_DATE_WIDTH_IN_DP = 80
private const val LEFT_AND_RIGHT_PADDING_EACH_DATE_IN_DP = 8
private const val ITEM_MID_POINT = (EACH_DATE_WIDTH_IN_DP + LEFT_AND_RIGHT_PADDING_EACH_DATE_IN_DP).div(2)

@OptIn(ExperimentalTime::class)
@Composable
fun HorizontalMonthDates(modifier: Modifier = Modifier) {

    //// Clock.System.now() -> Output: 2025-11-15T14:30:45.123456789Z
    ////                        (Year-Month-Day T Hour:Minute:Second.Nanoseconds Z for UTC)


    //// Output: 2025-11-15T20:00:45.123456789 (if you're in IST, UTC+5:30)
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    // Output: 2025-11-15

    val year = today.year // 2025

    // Always start from 1st January
    val startDate = LocalDate(year, 1, 1)
    val endDate = LocalDate(year, 12, 31)

    // Generate full year date list
    val dates: List<LocalDate> = remember {
        generateSequence(startDate) { date ->
            val next = date.plus(1, DateTimeUnit.DAY)
            if (next <= endDate) next else null
        }.toList()
    }

    // Selected item is today's date (even though list starts at Jan 1)
    var selectedDate by remember { mutableStateOf(today) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        DialerWeekCalendar(
            dates = dates,
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
        )
    }

}


@Composable
fun BoxWithConstraintsScope.DialerWeekCalendar(
    modifier: Modifier = Modifier,
    dates: List<LocalDate>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {

    val listState = rememberLazyListState()
    var isInitialLoad by remember { mutableStateOf(true) }

    // Auto-scroll to center the selected date
    LaunchedEffect(selectedDate) {
        val selectedIndex = dates.indexOf(selectedDate)
        if (selectedIndex != -1) {
            if (isInitialLoad) {
                listState.scrollToItem(selectedIndex)
                isInitialLoad = false
            } else {
                listState.animateScrollToItem(selectedIndex)
            }
        }
    }

    // no need of derivedState since maxWidth changes only once or minimal times
    val contentPadding = remember(maxWidth) {
        (maxWidth.value / 2 - ITEM_MID_POINT).dp
    }

    val layoutInfo by remember { derivedStateOf { listState.layoutInfo } }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(LEFT_AND_RIGHT_PADDING_EACH_DATE_IN_DP.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = contentPadding)
    ) {
        items(dates.size) { index ->
            val date = dates[index]

            // Center calculation
            val viewportCenter =
                layoutInfo.viewportStartOffset +
                        (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

            val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
            val itemCenter = itemInfo?.let { it.offset + it.size / 2 } ?: 0

            // Distance from center
            val distanceFromCenter = if (itemInfo != null) {
                abs(viewportCenter - itemCenter).toFloat() / layoutInfo.viewportSize.width
            } else 1f

            val scale = (1f - (distanceFromCenter * 0.3f)).coerceIn(0.7f, 1f)
            val rotationY = (distanceFromCenter * 40f).coerceAtMost(45f)
            val alpha = (1f - (distanceFromCenter * 0.5f)).coerceIn(0.5f, 1f)

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.rotationY =
                            if (itemCenter < viewportCenter) rotationY else -rotationY
                        this.alpha = alpha
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (date == selectedDate) Color(0xFF1E88E5) else Color(0xFFA9A9A9)
                    )
                    .clickable { onDateSelected(date) }
                    .width(EACH_DATE_WIDTH_IN_DP.dp)
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date.month.name.take(3),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (date == selectedDate) Color.White.copy(alpha = 0.8f)
                            else Color.DarkGray
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (date == selectedDate) Color.White else Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = date.dayOfWeek.name.take(3),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (date == selectedDate) Color.White.copy(alpha = 0.8f)
                            else Color.Gray
                        )
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalTime::class)
@AppPreview
@Composable
fun PreviewDialerWeekCalendar() {
    CenteredColumn() {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
        val year = today.year

        val startDate = LocalDate(year, 1, 1)
        val endDate = LocalDate(year, 12, 31)

        val dates = generateSequence(startDate) { date ->
            val next = date.plus(1, DateTimeUnit.DAY)
            if (next <= endDate) next else null
        }.toList()

        var selectedDate by remember { mutableStateOf(today) }

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            DialerWeekCalendar(
                dates = dates,
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it}
            )
        }
    }
}