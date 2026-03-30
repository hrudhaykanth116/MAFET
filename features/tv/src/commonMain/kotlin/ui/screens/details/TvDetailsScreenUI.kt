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
import coil3.compose.AsyncImage
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.modifier.gradientBackground
import com.hrudhaykanth116.core.ui.components.AppRoundedIcon
import com.hrudhaykanth116.core.ui.components.FancyChipsFlow
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.tv.domain.models.Network
import com.hrudhaykanth116.tv.domain.models.TvGenre
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_back
import mafet.core_ui.generated.resources.ic_bookmark

@Composable
fun TvDetailsScreenUI(
    state: TvDetailsScreenUIState,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onBookMarkClicked: (Int) -> Unit = {},
) {

    val tvShow = state.tvShowDetails ?: return

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box {
                if (!tvShow.posterPath.isNullOrEmpty()) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${tvShow.backdropPath}",
                        contentDescription = "${tvShow.name} poster",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.sdp),
                        contentScale = ContentScale.Crop
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
                    // Title
                    Text(
                        text = tvShow.name.ifEmpty { tvShow.originalName },
                        fontSize = 18.ssp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.sdp))

                    // First Air Date & Status
                    Row() {
                        Text(
                            text = "${tvShow.firstAirDate.orEmpty()} - ${tvShow.lastAirDate.orEmpty()}",
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
                        val voteAvg = tvShow.voteAverage
                        val formattedVote = ((voteAvg * 10).toInt() / 10.0).toString()
                        Text(
                            text = "$formattedVote / 10",
                            color = Color.White,
                            fontSize = 10.ssp,
                        )
                    }

                    Spacer(Modifier.height(8.sdp))

                    val genres = tvShow.genres.map { it.name }

                    if (genres.isNotEmpty()) {
                        FancyChipsFlow(
                            items = genres,
                        )
                    }


                    if (tvShow.networks.isNotEmpty()) {
                        VerticalSpacer()
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.sdp),
                            // contentPadding = PaddingValues(horizontal = 8.sdp)
                        ) {
                            items(tvShow.networks) { it: Network ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.sdp))
                                        .background(Color(0xFFD9D9D9))
                                        .padding(horizontal = 8.sdp, vertical = 4.sdp)
                                ) {
                                    if (it.logoPath != null && it.logoPath.isNotEmpty()) {
                                        AsyncImage(
                                            model = "https://image.tmdb.org/t/p/w500${it.logoPath}",
                                            contentDescription = it.name,
                                            modifier = Modifier
                                                .height(30.sdp)
                                                .width(60.sdp)
                                                .clip(RoundedCornerShape(4.sdp)),
                                            contentScale = ContentScale.Fit
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .height(30.sdp)
                                                .width(60.sdp)
                                                .clip(RoundedCornerShape(4.sdp))
                                                .background(Color.Gray),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = it.name,
                                                fontSize = 8.ssp,
                                                color = Color.White,
                                                modifier = Modifier.padding(4.sdp),
                                                maxLines = 2
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }



            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = Color(0xFF000000)
                    )
            ) {
                Spacer(Modifier.height(20.sdp))

                Text(
                    text = tvShow.overview,
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
                    onBookMarkClicked(tvShow.id)
                }
        )

    }

}


@AppPreview
@Composable
private fun TvDetailsScreenPreview() {

    val dummyTvShow = TvShowDetail(
        id = 1396,
        name = "Breaking Bad",
        overview = "When Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given only two years to live, he decides to risk everything by entering the meth business to secure his family’s future.",
        posterPath = "/ggFHVNu6YYI5L9pCfOacjizRGt.jpg",
        backdropPath = "/bzoZjhbpriBT2N5kwgK0weUfVOX.jpg",
        voteAverage = 8.9,
        voteCount = 14000,
        firstAirDate = "2008-01-20",
        lastAirDate = "2013-09-29",
        popularity = 200.5,
        originalLanguage = "en",
        originalName = "Breaking Bad",
        originCountry = listOf("US"),
        genres = listOf(
            TvGenre(id = 18, name = "Drama"),
            TvGenre(id = 80, name = "Crime")
        ),
        createdBy = listOf(
            com.hrudhaykanth116.tv.domain.models.Creator(
                id = 66633,
                name = "Vince Gilligan",
                creditId = "52e682cf9251415f28007e43",
                gender = 2,
                profilePath = "/uFh3OrBvkwKSU3N5y0XnXOhqBJz.jpg"
            )
        ),
        networks = listOf(
            Network(
                id = 174,
                name = "AMC",
                logoPath = "/alqLicR1ZMHMaZGP3xRQxn9sq7p.png",
                originCountry = "US"
            )
        ),
        productionCompanies = listOf(
            com.hrudhaykanth116.tv.domain.models.ProductionCompany(
                id = 11073,
                name = "High Bridge Entertainment",
                logoPath = "/aCbASRcI1MI7DXjPbSW9Fcv9pvF.png",
                originCountry = "US"
            )
        ),
        seasons = listOf(
            com.hrudhaykanth116.tv.domain.models.Season(
                id = 3572,
                name = "Season 1",
                overview = "Walter White’s transformation begins.",
                airDate = "2008-01-20",
                episodeCount = 7,
                posterPath = "/1yeVJox3rjo2jBKrrihIMj7uoS9.jpg",
                seasonNumber = 1
            ),
            com.hrudhaykanth116.tv.domain.models.Season(
                id = 3573,
                name = "Season 2",
                overview = "The empire grows as Walt dives deeper.",
                airDate = "2009-03-08",
                episodeCount = 13,
                posterPath = "/e3oGYpoTUhOFK0BJfloru5ZmGV.jpg",
                seasonNumber = 2
            )
        ),
        numberOfEpisodes = 62,
        numberOfSeasons = 5,
        episodeRunTime = listOf(47),
        lastEpisodeToAir = com.hrudhaykanth116.tv.domain.models.Episode(
            id = 62161,
            name = "Felina",
            overview = "The series finale: Walter White returns to Albuquerque to tie up loose ends.",
            airDate = "2013-09-29",
            episodeNumber = 16,
            seasonNumber = 5,
            showId = 1396,
            stillPath = "/r3z70vunihrAkjILQKWHX0G2xzO.jpg",
            voteAverage = 9.7,
            voteCount = 220,
            productionCode = "5AGH16"
        ),
        status = "Ended",
        type = "Scripted",
        homepage = "http://www.amc.com/shows/breaking-bad",
        inProduction = false,
        languages = listOf("en")
    )


    AppPreviewContainer {
        TvDetailsScreenUI(
            state = TvDetailsScreenUIState(
                tvShowDetails = dummyTvShow
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }

}
