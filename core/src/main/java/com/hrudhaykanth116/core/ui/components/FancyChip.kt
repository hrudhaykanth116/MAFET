package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FancyChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(50)
    val bg = Brush.horizontalGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC)))
    Box(
        modifier
            .shadow(6.dp, shape, clip = false)
            .clip(shape)
            .background(bg)
            .border(1.dp, Color.White.copy(alpha = 0.35f), shape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.labelLarge)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FancyChipsFlow(
    items: List<String>,
    modifier: Modifier = Modifier,
    horizontalGap: Int = 10,
    verticalGap: Int = 10,
    onChipClick: (String) -> Unit = {}
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(horizontalGap.dp),
        verticalArrangement = Arrangement.spacedBy(verticalGap.dp)
    ) {
        items.forEach { label ->
            FancyChip(text = label) { onChipClick(label) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFancyChipsFlow() {
    MaterialTheme {
        FancyChipsFlow(
            items = listOf(
                "Action", "Drama", "Comedy", "Thriller", "Sci-Fi", "Fantasy",
                "Romance", "Adventure", "Mystery", "Horror", "Animation",
                "Documentary", "Family", "War", "History", "Music", "Crime"
            )
        )
    }
}
