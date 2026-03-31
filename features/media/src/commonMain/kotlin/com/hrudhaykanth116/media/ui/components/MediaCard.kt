package com.hrudhaykanth116.media.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType

@Composable
fun MediaCard(
    item: MediaItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            AsyncImage(
                model = item.thumbnailUrl,
                contentDescription = "Photo by ${item.photographer}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(
                        ratio = item.width.toFloat() / item.height.toFloat()
                    )
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            if (item.type == MediaType.VIDEOS) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Video",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            item.avgColor?.let { colorHex ->
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .background(
                            color = parseHexColor(colorHex),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.photographer,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isColorDark(colorHex)) Color.White else Color.Black,
                        fontSize = 10.ssp
                    )
                }
            }
        }
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

private fun isColorDark(hex: String): Boolean {
    return try {
        val cleanHex = hex.removePrefix("#")
        val rgb = when (cleanHex.length) {
            6 -> cleanHex.toLong(16)
            8 -> cleanHex.toLong(16) and 0xFFFFFF
            else -> return false
        }
        val r = ((rgb shr 16) and 0xFF).toInt()
        val g = ((rgb shr 8) and 0xFF).toInt()
        val b = (rgb and 0xFF).toInt()
        val darkness = 1 - (0.299 * r + 0.587 * g + 0.114 * b) / 255
        darkness >= 0.5
    } catch (e: Exception) {
        false
    }
}
