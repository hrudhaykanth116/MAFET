package com.hrudhaykanth116.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}