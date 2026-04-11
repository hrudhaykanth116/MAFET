package com.hrudhaykanth116.tv.ui.screens.details.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.tv.ui.screens.details.AboutTabUIState
import com.hrudhaykanth116.tv.ui.screens.details.CastUIState
import com.hrudhaykanth116.tv.ui.screens.details.CreatorUIState
import com.hrudhaykanth116.tv.ui.screens.details.ProductionCompanyUIState
import com.hrudhaykanth116.tv.ui.screens.details.SeasonUIState

@Composable
fun AboutTabContent(
    overview: String,
    aboutState: AboutTabUIState?,
    modifier: Modifier = Modifier,
) {
    if (aboutState == null) {
        CenteredColumn(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 12.sdp),
    ) {
        // Overview
        if (overview.isNotBlank()) {
            Text(
                text = overview,
                color = Color.White,
                fontSize = 12.ssp,
                modifier = Modifier.padding(horizontal = 12.sdp),
            )
            VerticalSpacer(height = 16.sdp)
        }

        // if (state.networks.isNotEmpty()) {
        //     VerticalSpacer(height = 8.sdp)
        //     LazyRow(
        //         horizontalArrangement = Arrangement.spacedBy(8.sdp),
        //     ) {
        //         items(state.networks) { network ->
        //             Column(
        //                 horizontalAlignment = Alignment.CenterHorizontally,
        //                 modifier = Modifier
        //                     .clip(RoundedCornerShape(8.sdp))
        //                     .background(Color(0xFFD9D9D9))
        //                     .padding(horizontal = 8.sdp, vertical = 2.sdp),
        //             ) {
        //                 if (network.logo != null) {
        //                     AppImage(
        //                         imageSource = network.logo,
        //                         modifier = Modifier
        //                             .height(15.sdp)
        //                             .width(40.sdp)
        //                             .clip(RoundedCornerShape(4.sdp)),
        //                         contentScale = ContentScale.Fit,
        //                     )
        //                 } else {
        //                     Text(
        //                         text = network.name,
        //                         fontSize = 8.ssp,
        //                         color = Color.Blue,
        //                         fontWeight = FontWeight.Bold,
        //                         modifier = Modifier
        //                             .heightIn(min = 15.sdp, max = 15.sdp)
        //                             .padding(4.sdp),
        //                         maxLines = 2,
        //                     )
        //                 }
        //             }
        //         }
        //     }
        // }

        // Cast
        if (aboutState.cast.isNotEmpty()) {
            SectionTitle("Cast")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                contentPadding = PaddingValues(horizontal = 12.sdp),
            ) {
                items(aboutState.cast) { cast ->
                    CastItem(cast)
                }
            }
            VerticalSpacer(height = 16.sdp)
        }

        // Seasons
        if (aboutState.seasons.isNotEmpty()) {
            SectionTitle("Seasons")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                contentPadding = PaddingValues(horizontal = 12.sdp),
            ) {
                items(aboutState.seasons) { season ->
                    SeasonItem(season)
                }
            }
            VerticalSpacer(height = 16.sdp)
        }

        // Created By
        if (aboutState.creators.isNotEmpty()) {
            SectionTitle("Created By")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                contentPadding = PaddingValues(horizontal = 12.sdp),
            ) {
                items(aboutState.creators) { creator ->
                    CreatorItem(creator)
                }
            }
            VerticalSpacer(height = 16.sdp)
        }

        // Info rows
        Column(modifier = Modifier.padding(horizontal = 12.sdp)) {
            InfoRow("Status", aboutState.status)
            InfoRow("Type", aboutState.type)
            InfoRow("Seasons", aboutState.numberOfSeasons.toString())
            InfoRow("Episodes", aboutState.numberOfEpisodes.toString())
            if (aboutState.languages.isNotEmpty()) {
                InfoRow("Languages", aboutState.languages.joinToString(", "))
            }
        }

        // Production Companies
        if (aboutState.productionCompanies.isNotEmpty()) {
            VerticalSpacer(height = 12.sdp)
            SectionTitle("Production Companies")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                contentPadding = PaddingValues(horizontal = 12.sdp),
            ) {
                items(aboutState.productionCompanies) { company ->
                    ProductionCompanyItem(company)
                }
            }
        }

        VerticalSpacer(height = 16.sdp)
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 14.ssp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 12.sdp, vertical = 6.sdp),
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    if (value.isBlank()) return
    Text(
        text = "$label: $value",
        color = Color.LightGray,
        fontSize = 11.ssp,
        modifier = Modifier.padding(vertical = 2.sdp),
    )
}

@Composable
private fun CastItem(cast: CastUIState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(75.sdp),
    ) {
        if (cast.profileImage != null) {
            AppImage(
                imageSource = cast.profileImage,
                modifier = Modifier
                    .size(65.sdp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }
        VerticalSpacer(height = 4.sdp)
        Text(
            text = cast.name,
            color = Color.White,
            fontSize = 9.ssp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        if (cast.character.isNotBlank()) {
            Text(
                text = cast.character,
                color = Color.Gray,
                fontSize = 8.ssp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun SeasonItem(season: SeasonUIState) {
    Column(
        modifier = Modifier.width(100.sdp),
    ) {
        if (season.posterImage != null) {
            AppImage(
                imageSource = season.posterImage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.sdp)
                    .clip(RoundedCornerShape(8.sdp)),
                contentScale = ContentScale.Crop,
            )
        }
        VerticalSpacer(height = 4.sdp)
        Text(
            text = season.name,
            color = Color.White,
            fontSize = 10.ssp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "${season.episodeCount} episodes",
            color = Color.Gray,
            fontSize = 8.ssp,
        )
    }
}

@Composable
private fun CreatorItem(creator: CreatorUIState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(75.sdp),
    ) {
        if (creator.profileImage != null) {
            AppImage(
                imageSource = creator.profileImage,
                modifier = Modifier
                    .size(55.sdp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }
        VerticalSpacer(height = 4.sdp)
        Text(
            text = creator.name,
            color = Color.White,
            fontSize = 9.ssp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ProductionCompanyItem(company: ProductionCompanyUIState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.sdp),
    ) {
        if (company.logo != null) {
            AppImage(
                imageSource = company.logo,
                modifier = Modifier
                    .height(40.sdp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.sdp)),
                contentScale = ContentScale.Fit,
            )
        }
        VerticalSpacer(height = 4.sdp)
        Text(
            text = company.name,
            color = Color.Gray,
            fontSize = 8.ssp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
