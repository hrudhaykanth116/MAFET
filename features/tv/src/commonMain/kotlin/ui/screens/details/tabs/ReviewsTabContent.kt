package com.hrudhaykanth116.tv.ui.screens.details.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.tv.ui.screens.details.ReviewUIState
import com.hrudhaykanth116.tv.ui.screens.details.ReviewsTabUIState

@Composable
fun ReviewsTabContent(
    state: ReviewsTabUIState?,
    modifier: Modifier = Modifier,
) {
    if (state == null) {
        // TODO: Handle state gracefully
        CenteredColumn(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    if (state.reviews.isEmpty()) {
        CenteredColumn(modifier = modifier.fillMaxSize()) {
            Text(
                text = "No reviews yet",
                color = Color.Gray,
                fontSize = 12.ssp,
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.sdp, vertical = 12.sdp),
        verticalArrangement = Arrangement.spacedBy(10.sdp),
    ) {
        items(state.reviews, key = { it.id }) { review ->
            ReviewItem(review)
        }
    }
}

@Composable
private fun ReviewItem(review: ReviewUIState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.sdp))
            .background(Color(0xFF151515))
            .padding(12.sdp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (review.avatar != null) {
                AppImage(
                    imageSource = review.avatar,
                    modifier = Modifier
                        .size(32.sdp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Column(
                    modifier = Modifier
                        .size(32.sdp)
                        .clip(CircleShape)
                        .background(Color(0xFF333333)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = review.author.take(1).uppercase(),
                        color = Color.White,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            HorizontalSpacer(width = 10.sdp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = review.author,
                    color = Color.White,
                    fontSize = 11.ssp,
                    fontWeight = FontWeight.Bold,
                )
                if (review.createdAt.isNotBlank()) {
                    Text(
                        text = review.createdAt,
                        color = Color.Gray,
                        fontSize = 9.ssp,
                    )
                }
            }
            if (review.rating.isNotBlank()) {
                Text(
                    text = review.rating,
                    color = Color(0xFFFFC107),
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.sdp))
                        .background(Color(0xFF1F1F1F))
                        .padding(horizontal = 6.sdp, vertical = 2.sdp),
                )
            }
        }
        VerticalSpacer(height = 8.sdp)
        Text(
            text = review.content,
            color = Color.LightGray,
            fontSize = 10.ssp,
            lineHeight = 14.ssp,
        )
    }
}
