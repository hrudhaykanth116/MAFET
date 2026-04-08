package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.preview.MyPreview

/**
 * Represents a single button in [AppAlertDialog].
 *
 * @param label Button text.
 * @param onClick Invoked when the button is tapped.
 */
data class DialogAction(
    val label: UIText,
    val onClick: () -> Unit,
)

/**
 * Reusable alert dialog with up to three configurable action buttons.
 *
 * Button styling hierarchy (matches visual importance):
 * - [primaryAction]   → filled `Button`
 * - [secondaryAction] → `OutlinedButton`
 * - [tertiaryAction]  → `TextButton`
 *
 * With 1–2 actions the buttons are arranged in a trailing `Row`.
 * With 3 actions they stack in a full-width `Column`.
 *
 * @param title            Required dialog heading.
 * @param description      Optional body text shown below the title.
 * @param primaryAction    First (most prominent) action.
 * @param secondaryAction  Second action.
 * @param tertiaryAction   Third action.
 * @param onDismissRequest Called when the user taps outside or presses back.
 * @param isDismissable    Whether tapping outside closes the dialog.
 */
@Composable
fun AppAlertDialog(
    title: UIText,
    modifier: Modifier = Modifier,
    description: UIText? = null,
    primaryAction: DialogAction? = null,
    secondaryAction: DialogAction? = null,
    tertiaryAction: DialogAction? = null,
    onDismissRequest: () -> Unit = {},
    isDismissable: Boolean = true,
) {
    AppDialog(
        onDismissRequest = onDismissRequest,
        shouldCloseOnTouchOutSide = isDismissable,
    ) {
        Surface(
            modifier = modifier.widthIn(min = 280.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                AppText(
                    uiText = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                )

                if (description != null) {
                    VerticalSpacer(12.dp)
                    AppText(
                        uiText = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    )
                }

                val actions = listOfNotNull(primaryAction, secondaryAction, tertiaryAction)
                if (actions.isNotEmpty()) {
                    VerticalSpacer(24.dp)
                    if (actions.size <= 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, androidx.compose.ui.Alignment.End),
                        ) {
                            actions.forEachIndexed { index, action ->
                                DialogButton(index = index, action = action)
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            actions.forEachIndexed { index, action ->
                                DialogButton(
                                    index = index,
                                    action = action,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogButton(
    index: Int,
    action: DialogAction,
    modifier: Modifier = Modifier,
) {
    when (index) {
        0 -> Button(onClick = action.onClick, modifier = modifier) {
            AppText(uiText = action.label)
        }
        1 -> OutlinedButton(onClick = action.onClick, modifier = modifier) {
            AppText(uiText = action.label)
        }
        else -> TextButton(onClick = action.onClick, modifier = modifier) {
            AppText(uiText = action.label)
        }
    }
}

@MyPreview
@Composable
private fun AppAlertDialogOneButtonPreview() {
    AppAlertDialog(
        title = "Delete item?".toUIText(),
        description = "This action cannot be undone.".toUIText(),
        primaryAction = DialogAction(label = "Delete".toUIText(), onClick = {}),
        onDismissRequest = {},
        isDismissable = false,
    )
}

@MyPreview
@Composable
private fun AppAlertDialogTwoButtonPreview() {
    AppAlertDialog(
        title = "New update available".toUIText(),
        description = "Version 2.0 brings exciting new features and performance improvements.".toUIText(),
        primaryAction = DialogAction(label = "Update Now".toUIText(), onClick = {}),
        secondaryAction = DialogAction(label = "Later".toUIText(), onClick = {}),
    )
}

@MyPreview
@Composable
private fun AppAlertDialogThreeButtonPreview() {
    AppAlertDialog(
        title = "Stay in the loop".toUIText(),
        description = "Enable notifications to get personalized updates delivered to you.".toUIText(),
        primaryAction = DialogAction(label = "Enable".toUIText(), onClick = {}),
        secondaryAction = DialogAction(label = "Remind me later".toUIText(), onClick = {}),
        tertiaryAction = DialogAction(label = "Don't ask again".toUIText(), onClick = {}),
    )
}
