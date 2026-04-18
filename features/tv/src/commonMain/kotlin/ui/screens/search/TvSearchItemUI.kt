package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_add
import mafet.core_ui.generated.resources.ic_bookmark
import mafet.core_ui.generated.resources.ic_bookmark_filled
import mafet.core_ui.generated.resources.ic_check
import mafet.core_ui.generated.resources.image_place_holder

@Composable
fun TvSearchItemUI(
    state: SearchScreenItemUIState,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier = modifier,
        cornerPercent = 10,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.DEFAULT_PADDING),
        ) {
            // Poster image with rounded corners
            if (state.image != null) {
                AppImage(
                    imageSource = state.image,
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }

            HorizontalSpacer(width = 12.dp)

            // Content column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                // Title
                AppText(
                    uiText = state.name,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    overflow = TextOverflow.Ellipsis,
                )

                // Year and Rating row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // Year
                    val year = state.firstAirDate?.take(4)
                    if (!year.isNullOrBlank()) {
                        AppText(
                            uiText = year.toUIText(),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                    }

                    // Rating badge
                    if (state.rating > 0) {
                        RatingBadge(rating = state.rating)
                    }
                }

                VerticalSpacer(height = 2.dp)

                // Overview
                if (state.overview.isNotBlank()) {
                    AppText(
                        uiText = state.overview.toUIText(),
                        maxLines = 3,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            HorizontalSpacer(width = 8.dp)

            // Add/Check button
            Box(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                if (state.isMyTvList) {
                    AppIcon(
                        resource = Res.drawable.ic_bookmark_filled,
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                } else {
                    AppClickableIcon(
                        resource = Res.drawable.ic_bookmark,
                        modifier = Modifier.size(36.dp),
                        onClick = onAdd,
                        iconColor = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingBadge(rating: Double) {
    val formattedRating = ((rating * 10).toInt() / 10.0).toString()
    val ratingColor = when {
        rating >= 7.0 -> Color(0xFF4CAF50)
        rating >= 5.0 -> Color(0xFFFFA726)
        else -> Color(0xFFEF5350)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(RoundedCornerShape(50))
                .background(ratingColor)
        )
        AppText(
            uiText = formattedRating.toUIText(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = ratingColor,
            ),
        )
    }
}

@AppPreview
@Composable
private fun TvSearchItemUIPreview() {
    AppPreviewContainer {
        Column(
            modifier = Modifier.padding(Dimens.DEFAULT_PADDING),
            verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        ) {
            // High-rated show with overview
            TvSearchItemUI(
                state = SearchScreenItemUIState(
                    id = 1,
                    name = "Breaking Bad".toUIText(),
                    image = ImageHolder.LocalDrawableResource(Res.drawable.image_place_holder),
                    isMyTvList = false,
                    overview = "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student.",
                    rating = 8.9,
                    firstAirDate = "2008-01-20",
                ),
                onAdd = {},
            )

            // Already in watchlist, no overview
            TvSearchItemUI(
                state = SearchScreenItemUIState(
                    id = 2,
                    name = "Hunter x Hunter".toUIText(),
                    image = ImageHolder.LocalDrawableResource(Res.drawable.image_place_holder),
                    isMyTvList = true,
                    overview = "",
                    rating = 8.6,
                    firstAirDate = "2011-10-02",
                ),
                onAdd = {},
            )

            // Low-rated, no image
            TvSearchItemUI(
                state = SearchScreenItemUIState(
                    id = 3,
                    name = "Some Obscure Show With a Really Long Title That Should Ellipsize".toUIText(),
                    image = null,
                    isMyTvList = false,
                    overview = "A short description.",
                    rating = 3.2,
                    firstAirDate = null,
                ),
                onAdd = {},
            )
        }
    }
}
