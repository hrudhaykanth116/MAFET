package com.hrudhaykanth116.tv.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.modifier.gradientBackground
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.AppRoundedIcon
import com.hrudhaykanth116.core.ui.components.FancyChipsFlow
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_back
import mafet.core_ui.generated.resources.ic_bookmark
import mafet.core_ui.generated.resources.image_place_holder

@Composable
fun TvDetailsScreenUI(
    state: TvDetailsScreenUIState,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onBookMarkClicked: (Int) -> Unit = {},
) {

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box {
                if (state.backdropImage != null) {
                    AppImage(
                        imageSource = state.backdropImage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.sdp),
                        contentScale = ContentScale.Crop,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.sdp)
                        .gradientBackground(
                            listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color(0xFF000000),
                            )
                        )
                        .padding(horizontal = 8.sdp, vertical = 10.sdp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = state.title,
                        fontSize = 18.ssp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.sdp))

                    Row {
                        Text(
                            text = state.dateRange,
                            fontSize = 10.ssp,
                            color = Color.White
                        )
                        HorizontalSpacer(width = 1.sdp)
                        Text(
                            text = " | ",
                            fontSize = 10.ssp,
                            color = Color.White
                        )
                        HorizontalSpacer(width = 1.sdp)
                        Text(
                            text = state.rating,
                            color = Color.White,
                            fontSize = 10.ssp,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = Color(0xFF000000)
                    )
                    .padding(horizontal = 8.sdp, vertical = 8.sdp),
                ) {

                if (state.genres.isNotEmpty()) {
                    FancyChipsFlow(
                        items = state.genres,
                    )
                }

                if (state.networks.isNotEmpty()) {
                    VerticalSpacer(height = 8.sdp)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.sdp),
                    ) {
                        items(state.networks) { network ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.sdp))
                                    .background(Color(0xFFD9D9D9))
                                    .padding(horizontal = 8.sdp, vertical = 2.sdp)
                            ) {
                                if (network.logo != null) {
                                    AppImage(
                                        imageSource = network.logo,
                                        modifier = Modifier
                                            .height(15.sdp)
                                            .width(40.sdp)
                                            .clip(RoundedCornerShape(4.sdp)),
                                        contentScale = ContentScale.Fit,
                                    )
                                } else {
                                    Text(
                                        text = network.name,
                                        fontSize = 8.ssp,
                                        color = Color.Blue, fontWeight = FontWeight.Bold,
                                        modifier = Modifier.heightIn(min = 15.sdp, max = 15.sdp).padding(4.sdp),
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.sdp))

                Text(
                    text = state.overview,
                    color = Color.White,
                    fontSize = 12.ssp,
                    modifier = Modifier.padding(horizontal = 8.sdp)
                )
            }
        }

        AppRoundedIcon(
            icon = Res.drawable.ic_back,
            tint = Color.White,
            iconSize = 30.sdp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = 10.sdp, x = 10.sdp)
                .clickable {
                    onBackClicked()
                }
        )

        AppRoundedIcon(
            icon = Res.drawable.ic_bookmark,
            tint = Color.White,
            iconSize = 30.sdp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = 10.sdp, x = (-10).sdp)
                .clickable {
                    onBookMarkClicked(state.id)
                }
        )

    }

}


@AppPreview
@Composable
private fun TvDetailsScreenPreview() {
    AppPreviewContainer {
        TvDetailsScreenUI(
            state = TvDetailsScreenUIState(
                id = 1396,
                title = "Breaking Bad",
                overview = "When Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given only two years to live, he decides to risk everything by entering the meth business to secure his family's future.",
                backdropImage = ImageHolder.LocalDrawableResource(Res.drawable.image_place_holder),
                dateRange = "2008-01-20 - 2013-09-29",
                rating = "8.9 / 10",
                genres = listOf("Drama", "Crime", "Action & Adventure"),
                networks = listOf(
                    NetworkUIState(
                        name = "AMC",
                        logo = ImageHolder.LocalDrawableResource(Res.drawable.image_place_holder),
                    ),
                    NetworkUIState(
                        name = "HBO",
                        logo = null
                    )
                ),
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
