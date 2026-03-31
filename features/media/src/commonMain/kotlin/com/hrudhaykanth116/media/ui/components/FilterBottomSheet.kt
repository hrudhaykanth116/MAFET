package com.hrudhaykanth116.media.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.media.domain.models.ColorFilter
import com.hrudhaykanth116.media.domain.models.FilterState
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.domain.models.OrientationType
import com.hrudhaykanth116.media.domain.models.SizeFilter
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilters: FilterState,
    mediaType: MediaType,
    onApply: (FilterState) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var tempFilters by remember { mutableStateOf(currentFilters) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.headlineSmall
                )

                Row {
                    OutlinedButton(
                        onClick = {
                            tempFilters = FilterState()
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Reset", fontSize = 13.ssp)
                    }

                    Button(
                        onClick = {
                            onApply(tempFilters)
                            scope.launch {
                                sheetState.hide()
                                onDismiss()
                            }
                        }
                    ) {
                        Text("Apply", fontSize = 13.ssp)
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Orientation",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrientationType.entries.forEach { orientation ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            tempFilters = tempFilters.copy(orientation = orientation)
                        }
                    ) {
                        RadioButton(
                            selected = tempFilters.orientation == orientation,
                            onClick = { tempFilters = tempFilters.copy(orientation = orientation) }
                        )
                        Text(
                            text = when (orientation) {
                                OrientationType.ALL -> "All"
                                OrientationType.PORTRAIT -> "Portrait"
                                OrientationType.LANDSCAPE -> "Landscape"
                                OrientationType.SQUARE -> "Square"
                            },
                            fontSize = 13.ssp
                        )
                    }
                }
            }

            if (mediaType == MediaType.PHOTOS) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Colors",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ColorFilter.entries.forEach { colorFilter ->
                        ColorChip(
                            color = parseHexColor(colorFilter.hexColor),
                            label = colorFilter.displayName,
                            isSelected = tempFilters.color == colorFilter,
                            onClick = {
                                tempFilters = tempFilters.copy(color = colorFilter)
                            }
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Size",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SizeFilter.entries.forEach { size ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                tempFilters = tempFilters.copy(size = size)
                            }
                        ) {
                            RadioButton(
                                selected = tempFilters.size == size,
                                onClick = { tempFilters = tempFilters.copy(size = size) }
                            )
                            Text(
                                text = when (size) {
                                    SizeFilter.ALL -> "All"
                                    SizeFilter.LARGE -> "Large"
                                    SizeFilter.MEDIUM -> "Medium"
                                    SizeFilter.SMALL -> "Small"
                                },
                                fontSize = 13.ssp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorChip(
    color: Color,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color)
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = if (label == "Black" || label == "Brown") Color.White else Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = label,
            fontSize = 10.ssp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

private fun parseHexColor(hex: String): Color {
    return try {
        val cleanHex = hex.removePrefix("#")
        val rgb = when (cleanHex.length) {
            6 -> cleanHex.toLong(16)
            8 -> cleanHex.toLong(16) and 0xFFFFFF
            else -> return Color.Gray
        }
        Color(
            red = ((rgb shr 16) and 0xFF).toInt(),
            green = ((rgb shr 8) and 0xFF).toInt(),
            blue = (rgb and 0xFF).toInt()
        )
    } catch (e: Exception) {
        Color.Gray
    }
}
