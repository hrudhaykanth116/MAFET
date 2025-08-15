package com.hrudhaykanth116.tv.ui.screens.updatemytv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppCircularImage
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppDatePicker
import com.hrudhaykanth116.core.ui.components.AppDialog
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppProgressBar
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.AppTextField
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import com.hrudhaykanth116.core.R as CoreR

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UpdateMyTvScreenUI(
    updateMyTvScreenCallbacks: UpdateMyTvScreenCallbacks,
    stateActual: UpdateMyTvUIStateActual,
    modifier: Modifier = Modifier,
) {

    val bottomSheetState: SheetState = rememberModalBottomSheetState()


    // ModalBottomSheet(
    //     onDismissRequest = updateMyTvScreenCallbacks.onCancelled,
    //     sheetState = bottomSheetState,
    //     windowInsets = WindowInsets.navigationBars
    // ) {
    // }

    AppDialog() {
        UpdateMyTvScreenUIContent(stateActual, updateMyTvScreenCallbacks, modifier)
    }


// BottomSheetScaffold(sheetContent = {
//     // ModalBottomSheet(
//     //     onDismissRequest = updateMyTvScreenCallbacks.onCancelled,
//     //     sheetState = bottomSheetState,
//     // )
//     UpdateMyTvScreenUIContent(state)
// }) {
//
// }

}

@Composable
fun UpdateMyTvScreenUIContent(
    state: UpdateMyTvUIStateActual,
    updateMyTvScreenCallbacks: UpdateMyTvScreenCallbacks,
    modifier: Modifier,
) {

    // TODO: Should not be the case of null, check this
    val updateTvData = state.updateTvData ?: return

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10)
            )
            .padding(Dimens.DEFAULT_PADDING)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.DEFAULT_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AppCircularImage(
                image = updateTvData.imgSource,
                modifier = Modifier.size(100.dp)
            )
            VerticalSpacer()
            AppText(
                uiText = updateTvData.name.toUIText(),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            VerticalSpacer()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppText(uiText = "S".toUIText())
                HorizontalSpacer()
                // TODO: Make this popup with seasons list
                AppTextField(
                    textFieldData = TextFieldData(updateTvData.lastWatchedSeason),
                    modifier = Modifier.requiredWidth(50.dp),
                    singleLine = true,
                    onInputChange = updateMyTvScreenCallbacks.onSeasonChanged
                )
                HorizontalSpacer()
                AppText(uiText = "E".toUIText())
                HorizontalSpacer()
                // TODO: Make this popup with episodes list
                AppTextField(
                    textFieldData = TextFieldData(updateTvData.lastWatchedEpisode),
                    modifier = Modifier.requiredWidth(70.dp),
                    singleLine = true,
                    onInputChange = updateMyTvScreenCallbacks.onEpisodeChanged
                )
            }
            VerticalSpacer()
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppTextField(
                    textFieldData = TextFieldData(updateTvData.lastWatchedTimeUIText),
                    readOnly = true,
                    trailingIcon = {
                        AppClickableIcon(
                            resId = CoreR.drawable.ic_calendar,
                            onClick = {
                                updateMyTvScreenCallbacks.onLastWatchedDatePickerOpenRequest()
                            }
                        )
                    },
                    modifier = Modifier.width(150.dp)
                )
            }
            VerticalSpacer()
            Row {
                AppFormButton(btnText = "Cancel".toUIText()) { updateMyTvScreenCallbacks.onCancelled() }
                HorizontalSpacer()
                AppFormButton(btnText = "Submit".toUIText()) { updateMyTvScreenCallbacks.onSubmit() }
            }
            if (state.isLastWatchedDatePickerOpened) {
                AppDatePicker(
                    onDateSelected = {
                        updateMyTvScreenCallbacks.onLastWatchedDateChanged(it)
                    },
                    onDismissRequest = {
                        updateMyTvScreenCallbacks.onLastWatchedDatePickerCloseRequest()
                    }
                )
            }

        }
        if (state.isLoading) {
            // TODO: Prevent touch
            AppProgressBar()
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
                    name = "name",
                    lastWatchedSeason = TextFieldValue("1"),
                    lastWatchedEpisode = TextFieldValue("22"),
                    lastWatchedTime = null,
                    lastWatchedTimeUIText = TextFieldValue("12/5/2021"),
                    imgSource = CoreR.drawable.ic_calendar.toImageHolder()
                )
            ),
            updateMyTvScreenCallbacks = UpdateMyTvScreenCallbacks({}, {}, {}, {}),
            modifier = Modifier
        )

    }
}