package com.hrudhaykanth116.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AppDialog(
    onDismissRequest: () -> Unit = {},
    shouldCloseOnTouchOutSide: Boolean = false,
    content: @Composable () -> Unit,
) {

    Dialog(
        properties = DialogProperties(),
        onDismissRequest = {
            if (shouldCloseOnTouchOutSide) {
                onDismissRequest()
            }
        },
        content = content,
    )


}