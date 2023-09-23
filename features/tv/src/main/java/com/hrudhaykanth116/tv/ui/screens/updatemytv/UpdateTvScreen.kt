package com.hrudhaykanth116.tv.ui.screens.updatemytv

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.AppTextField
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIState

/**
 * This may become a quick popup dialog or entirely another screen.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UpdateTvScreen(
    state: UpdateMyTvUIState,
    updateMyTvScreenCallbacks: UpdateMyTvScreenCallbacks,
) {
    ModalBottomSheet(
        onDismissRequest = updateMyTvScreenCallbacks.onCancelled
    ) {

        AppText(uiText = state.name)
        VerticalSpacer()
        Row {
            AppText(uiText = "S".toUIText())
            // TODO: Make this popup with seasons list
            AppTextField(
                textFieldData = TextFieldData(TextFieldValue(text = state.lastWatchedSeason.toString())),

                )
            AppText(uiText = "E".toUIText())
            // TODO: Make this popup with episodes list
            AppTextField(textFieldData = TextFieldData(TextFieldValue(text = state.lastWatchedEpisode.toString())))
        }
        Row {
            AppText(uiText = "S".toUIText())
            // TODO: Make this popup with seasons list
            AppTextField(textFieldData = TextFieldData(TextFieldValue(text = state.lastWatchedTime.getText())))
        }

    }
}