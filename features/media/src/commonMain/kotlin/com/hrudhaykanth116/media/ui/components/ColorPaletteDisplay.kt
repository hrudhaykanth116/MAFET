package com.hrudhaykanth116.media.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.platform.ssp

@Composable
fun ColorPaletteDisplay(
    dominantColor: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Color:",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 13.ssp
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(parseHexColor(dominantColor))
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )

        Text(
            text = dominantColor,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.ssp
        )
    }
}

private fun parseHexColor(hex: String): Color {
    return try {
        val cleanHex = hex.removePrefix("#")
        val rgb = when (cleanHex.length) {
            6 -> cleanHex.toLong(16)
            8 -> cleanHex.toLong(16) and 0xFFFFFF
            else -> return Color.Gray
        }
        Color(
            red = ((rgb shr 16) and 0xFF).toInt(),
            green = ((rgb shr 8) and 0xFF).toInt(),
            blue = (rgb and 0xFF).toInt()
        )
    } catch (e: Exception) {
        Color.Gray
    }
}
