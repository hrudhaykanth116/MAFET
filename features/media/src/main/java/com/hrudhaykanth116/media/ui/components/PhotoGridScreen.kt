package com.hrudhaykanth116.media.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.PhotoSrc

@Composable
fun PhotoGridScreen(photos: List<PhotoResponse>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(photos) { photo ->
            PhotoGridItem(photo)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoGridPreview() {
    val samplePhotos = List(4) {
        PhotoResponse(
            id = it,
            url = "https://www.pexels.com/photo/sample-$it",
            photographer = "Photographer $it",
            src = PhotoSrc(
                original = "https://images.pexels.com/photos/32812556/pexels-photo-32812556.jpeg",
                medium = "https://images.pexels.com/photos/32812556/pexels-photo-32812556.jpeg?auto=compress&cs=tinysrgb&h=350",
                small = "https://images.pexels.com/photos/32812556/pexels-photo-32812556.jpeg?auto=compress&cs=tinysrgb&h=130"
            )
        )
    }

    PhotoGridScreen(samplePhotos)
}