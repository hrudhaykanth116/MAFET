package com.hrudhaykanth116.media.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.media.domain.models.MediaType

@Composable
fun MediaTypeToggle(
    selectedType: MediaType,
    onTypeSelected: (MediaType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        FilterChip(
            selected = selectedType == MediaType.PHOTOS,
            onClick = { onTypeSelected(MediaType.PHOTOS) },
            label = {
                Text(
                    text = "Photos",
                    fontSize = 14.ssp
                )
            },
            modifier = Modifier.padding(end = 8.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        FilterChip(
            selected = selectedType == MediaType.VIDEOS,
            onClick = { onTypeSelected(MediaType.VIDEOS) },
            label = {
                Text(
                    text = "Videos",
                    fontSize = 14.ssp
                )
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}
