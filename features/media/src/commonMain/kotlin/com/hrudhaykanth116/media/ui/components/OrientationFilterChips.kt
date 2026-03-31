package com.hrudhaykanth116.media.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.media.domain.models.OrientationType

@Composable
fun OrientationFilterChips(
    selectedOrientation: OrientationType,
    onOrientationSelected: (OrientationType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OrientationType.entries.forEach { orientation ->
            FilterChip(
                selected = selectedOrientation == orientation,
                onClick = { onOrientationSelected(orientation) },
                label = {
                    Text(
                        text = when (orientation) {
                            OrientationType.ALL -> "All"
                            OrientationType.PORTRAIT -> "Portrait"
                            OrientationType.LANDSCAPE -> "Landscape"
                            OrientationType.SQUARE -> "Square"
                        },
                        fontSize = 13.ssp
                    )
                },
                modifier = Modifier.padding(end = 8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}
