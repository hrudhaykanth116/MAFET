package com.hrudhaykanth116.composeapp.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.composeapp.domain.model.AppGateConfig
import com.hrudhaykanth116.composeapp.domain.model.GateButton
import com.hrudhaykanth116.core.ui.components.AppDialog
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.preview.MyPreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppGateDialog(
    gate: AppGateConfig,
    onAction: (action: String) -> Unit,
) {
    AppDialog(
        shouldCloseOnTouchOutSide = false,
    ) {
        Surface(
            modifier = Modifier.widthIn(min = 280.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = gate.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                )

                if (gate.message.isNotBlank()) {
                    VerticalSpacer(12.dp)
                    Text(
                        text = gate.message,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    )
                }

                if (gate.buttons.isNotEmpty()) {
                    VerticalSpacer(24.dp)
                    FlowRow(
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                            8.dp
                        ),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                            8.dp
                        ),
                    ) {
                        gate.buttons.forEachIndexed { index, button ->
                            GateButtonItem(
                                button = button,
                                isPrimary = index == 0,
                                onClick = { onAction(button.action) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GateButtonItem(
    button: GateButton,
    isPrimary: Boolean,
    onClick: () -> Unit,
) {
    if (isPrimary) {
        Button(onClick = onClick) {
            Text(button.text)
        }
    } else {
        OutlinedButton(onClick = onClick) {
            Text(button.text)
        }
    }
}

@Composable
@MyPreview
private fun AppGateDialogPreview(){

    AppPreviewContainer {
        AppGateDialog(
            gate = AppGateConfig(
                isEnabled = true,
                title = "New update available",
                message = "Version 2.0 brings exciting new features and performance improvements.",
                buttons = listOf(
                    GateButton(text = "Update Now", action = "update"),
                    GateButton(text = "Later", action = "later"),
                )
            ),
            onAction = {}
        )
    }

}