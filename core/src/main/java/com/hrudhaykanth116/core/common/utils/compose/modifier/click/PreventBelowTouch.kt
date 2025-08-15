package com.hrudhaykanth116.core.common.utils.compose.modifier.click

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

// Note: In previous versions of Compose, we recommended against this approach and suggested using composed {} instead via a lint rule. Now that composed {} is not recommended, the lint rule has been removed.
// from https://developer.android.com/develop/ui/compose/custom-modifiers
@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.preventBelowTouch(

): Modifier {

    return this.then(
        Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            // Prevent below touch events from passing through
        }
    )

    // return this.then(
    //     Modifier.pointerInput(Unit) {
    //         awaitPointerEventScope {
    //             while (true) {
    //                 val event = awaitPointerEvent(PointerEventPass.Initial)
    //                 event.changes.forEach { it.consume() } // Consumes the event so it doesn't pass through
    //             }
    //         }
    //     }
    // )

}