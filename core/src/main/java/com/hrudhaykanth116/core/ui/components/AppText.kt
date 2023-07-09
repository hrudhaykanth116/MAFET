package com.hrudhaykanth116.core.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.data.models.UIText

@Composable
fun AppText(
    text: UIText,
    modifier: Modifier = Modifier
) {

    Text(
        text = text.getText(),
        modifier = modifier
    )
}