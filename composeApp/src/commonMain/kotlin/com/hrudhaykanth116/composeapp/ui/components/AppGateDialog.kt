package com.hrudhaykanth116.composeapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.hrudhaykanth116.composeapp.models.AppGateConfig
import com.hrudhaykanth116.composeapp.models.GateType

@Composable
fun AppGateDialog(config: AppGateConfig) {
    val isDismissable = config.type == GateType.SOFT || config.type == GateType.MESSAGE
    val hasPlayStoreAction = config.type == GateType.FORCE || config.type == GateType.SOFT

    AlertDialog(
        onDismissRequest = { /* block back-press dismiss for non-dismissable types */ },
        title = { Text(config.title) },
        text = { Text(config.message) },
        confirmButton = {
            if (config.type != GateType.MAINTENANCE) {
                TextButton(onClick = {
                    if (hasPlayStoreAction) {
                        // openPlayStore(context)
                    }
                }) {
                    Text(config.ctaLabel)
                }
            }
        },
        dismissButton = {
            if (isDismissable) {
                TextButton(onClick = { /* no-op: handled by the screen via state */ }) {
                    Text("Later")
                }
            }
        },
    )
}

// private fun openPlayStore(context: android.content.Context) {
//     val packageName = context.packageName
//     try {
//         context.startActivity(
//             Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
//         )
//     } catch (_: android.content.ActivityNotFoundException) {
//         context.startActivity(
//             Intent(
//                 Intent.ACTION_VIEW,
//                 Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
//             )
//         )
//     }
// }
