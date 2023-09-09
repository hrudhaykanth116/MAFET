package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

@Composable
fun AppClickableIcon(
    imageHolder: ImageHolder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescriptionUIText: UIText? = null,
    enabled: Boolean = true,
    iconBackgroundColor: Color = Color.Unspecified,
    iconColor: Color = Color.Unspecified,
    disabledContainerColor: Color = Color.Unspecified,
    disabledContentColor: Color = iconColor.copy(alpha = 0.38f),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            iconBackgroundColor,
            iconColor,
            disabledContainerColor,
            disabledContentColor
        ),
        interactionSource = interactionSource
    ) {
        AppIcon(
            imageHolder = imageHolder,
            modifier = Modifier,
            contentDescriptionUIText = contentDescriptionUIText,
            tint = iconColor
        )
    }
}