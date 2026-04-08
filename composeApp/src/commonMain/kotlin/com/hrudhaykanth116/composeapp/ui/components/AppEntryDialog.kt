package com.hrudhaykanth116.composeapp.ui.components

import androidx.compose.runtime.Composable
import com.hrudhaykanth116.composeapp.models.AppEntryDialogRemoteConfig
import com.hrudhaykanth116.core.ui.components.AppAlertDialog
import com.hrudhaykanth116.core.ui.components.DialogAction
import com.hrudhaykanth116.core.ui.models.toUIText

/**
 * Dialog driven entirely by [AppEntryDialogRemoteConfig] fetched from Remote Config.
 *
 * Each button's [action] string is forwarded to [onButtonAction] so the caller
 * can implement action-specific navigation or behaviour without coupling this
 * composable to any particular flow.
 *
 * @param config         Remote-config model describing the dialog content.
 * @param onDismiss      Called when the dialog is dismissed (back-press or outside tap).
 * @param onButtonAction Called with the button's action tag when any button is tapped.
 */
@Composable
fun AppEntryDialog(
    config: AppEntryDialogRemoteConfig,
    onDismiss: () -> Unit,
    onButtonAction: (action: String) -> Unit,
) {
    val actions = config.buttons
        .filter { it.text.isNotBlank() }
        .take(3)
        .mapIndexed { index, btn ->
            DialogAction(
                label = btn.text.toUIText(),
                onClick = { onButtonAction(btn.action) },
            )
        }

    AppAlertDialog(
        title = config.title.toUIText(),
        description = config.description.takeIf { it.isNotBlank() }?.toUIText(),
        primaryAction = actions.getOrNull(0),
        secondaryAction = actions.getOrNull(1),
        tertiaryAction = actions.getOrNull(2),
        onDismissRequest = onDismiss,
        isDismissable = config.isDismissable,
    )
}
