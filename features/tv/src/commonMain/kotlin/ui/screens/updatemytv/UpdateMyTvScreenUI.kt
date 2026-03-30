package com.hrudhaykanth116.tv.ui.screens.updatemytv

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hrudhaykanth116.core.ui.components.AppCircularImage
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppDatePicker
import com.hrudhaykanth116.core.ui.components.AppProgressBar
import com.hrudhaykanth116.core.ui.components.inputtexts.AppTextField
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.tv.data.datasources.local.models.WatchStatus
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_calendar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UpdateMyTvScreenUI(
    updateMyTvScreenCallbacks: UpdateMyTvScreenCallbacks,
    stateActual: UpdateMyTvUIStateActual,
    modifier: Modifier = Modifier,
) {
    val bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = updateMyTvScreenCallbacks.onCancelled,
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        UpdateMyTvScreenUIContent(
            state = stateActual,
            updateMyTvScreenCallbacks = updateMyTvScreenCallbacks,
            modifier = modifier
        )
    }
}

@Composable
fun UpdateMyTvScreenUIContent(
    state: UpdateMyTvUIStateActual,
    updateMyTvScreenCallbacks: UpdateMyTvScreenCallbacks,
    modifier: Modifier = Modifier,
) {
    val updateTvData = state.updateTvData ?: return

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with image and name
            ShowHeader(
                name = updateTvData.name,
                imgSource = updateTvData.imgSource
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            // Season and Episode pickers
            SeasonEpisodeSection(
                season = updateTvData.lastWatchedSeason,
                episode = updateTvData.lastWatchedEpisode,
                onSeasonChanged = updateMyTvScreenCallbacks.onSeasonChanged,
                onEpisodeChanged = updateMyTvScreenCallbacks.onEpisodeChanged
            )

            // Status dropdown
            StatusSection(
                selectedStatus = updateTvData.status,
                onStatusChanged = updateMyTvScreenCallbacks.onStatusChanged
            )

            // Rating slider
            RatingSection(
                rating = updateTvData.rating,
                onRatingChanged = updateMyTvScreenCallbacks.onRatingChanged
            )

            // Last watched date
            DateSection(
                dateText = updateTvData.lastWatchedTimeUIText,
                onDatePickerRequest = updateMyTvScreenCallbacks.onLastWatchedDatePickerOpenRequest
            )

            // Notes field
            NotesSection(
                notes = updateTvData.notes,
                onNotesChanged = updateMyTvScreenCallbacks.onNotesChanged
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Action buttons
            ActionButtons(
                onCancel = updateMyTvScreenCallbacks.onCancelled,
                onSave = updateMyTvScreenCallbacks.onSubmit
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                AppProgressBar()
            }
        }

        if (state.isLastWatchedDatePickerOpened) {
            AppDatePicker(
                onDateSelected = { updateMyTvScreenCallbacks.onLastWatchedDateChanged(it) },
                onDismissRequest = { updateMyTvScreenCallbacks.onLastWatchedDatePickerCloseRequest() }
            )
        }
    }
}

@Composable
private fun ShowHeader(
    name: String,
    imgSource: ImageHolder?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppCircularImage(
            image = imgSource,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SeasonEpisodeSection(
    season: TextFieldValue,
    episode: TextFieldValue,
    onSeasonChanged: (TextFieldValue) -> Unit,
    onEpisodeChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Progress",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Season",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppTextField(
                    textFieldData = TextFieldData(season),
                    modifier = Modifier.width(70.dp),
                    singleLine = true,
                    onInputChange = onSeasonChanged
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Episode",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppTextField(
                    textFieldData = TextFieldData(episode),
                    modifier = Modifier.width(70.dp),
                    singleLine = true,
                    onInputChange = onEpisodeChanged
                )
            }
        }
    }
}

@Composable
private fun StatusSection(
    selectedStatus: WatchStatus,
    onStatusChanged: (WatchStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Status",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatusIndicator(status = selectedStatus)
                        Text(
                            text = selectedStatus.displayName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "▼",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                WatchStatus.entries.forEach { status ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StatusIndicator(status = status)
                                Text(text = status.displayName)
                            }
                        },
                        onClick = {
                            onStatusChanged(status)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusIndicator(status: WatchStatus) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(Color(status.colorValue))
    )
}

@Composable
private fun RatingSection(
    rating: Int?,
    onRatingChanged: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val sliderValue = rating?.toFloat() ?: 0f

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rating",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = if (rating != null) "$rating/10" else "Not rated",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = if (rating != null) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = sliderValue,
            onValueChange = { onRatingChanged(it.toInt().takeIf { v -> v > 0 }) },
            valueRange = 0f..10f,
            steps = 9,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFFFD700),
                activeTrackColor = Color(0xFFFFD700),
                inactiveTrackColor = Color(0xFFFFD700).copy(alpha = 0.3f)
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "0",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "10",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DateSection(
    dateText: TextFieldValue,
    onDatePickerRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Last Watched",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppTextField(
            textFieldData = TextFieldData(dateText),
            readOnly = true,
            trailingIcon = {
                AppClickableIcon(
                    resource = Res.drawable.ic_calendar,
                    onClick = onDatePickerRequest
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun NotesSection(
    notes: TextFieldValue,
    onNotesChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            placeholder = {
                Text(
                    text = "Add your thoughts about this show...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            },
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
private fun ActionButtons(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Cancel")
        }
        Button(
            onClick = onSave,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Save")
        }
    }
}

@AppPreview
@Composable
private fun UpdateMyTvScreenUIContentPreview() {
    AppPreviewContainer {
        UpdateMyTvScreenUIContent(
            state = UpdateMyTvUIStateActual(
                updateTvData = UpdateMyTvUIStateActual.UpdateTvData(
                    id = 1,
                    name = "Breaking Bad",
                    lastWatchedSeason = TextFieldValue("5"),
                    lastWatchedEpisode = TextFieldValue("16"),
                    lastWatchedTime = null,
                    lastWatchedTimeUIText = TextFieldValue("12/5/2021"),
                    imgSource = Res.drawable.ic_calendar.toImageHolder(),
                    status = WatchStatus.WATCHING,
                    rating = 9,
                    notes = TextFieldValue("Best show ever!")
                )
            ),
            updateMyTvScreenCallbacks = UpdateMyTvScreenCallbacks()
        )
    }
}
