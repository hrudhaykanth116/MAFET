package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

@Composable
fun AppClickableIcon(
    imageHolder: ImageHolder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescriptionUIText: UIText? = null,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled, colors, interactionSource
    ) {
        AppIcon(
            type = imageHolder,
            modifier = Modifier,
            contentDescriptionUIText = contentDescriptionUIText,
            // tint = , tint will be taken from colors.
        )
    }
}