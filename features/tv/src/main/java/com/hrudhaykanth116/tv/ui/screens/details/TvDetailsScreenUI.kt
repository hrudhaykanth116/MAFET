package com.hrudhaykanth116.tv.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre

@Composable
fun TvDetailsScreenUI(
    state: TvDetailsScreenUIState,
    processEvent: (TvDetailsScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    val tvShow = state.tvShowDetails

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // allow scrolling
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Poster
        if (!tvShow.posterPath.isNullOrEmpty()) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${tvShow.posterPath}",
                contentDescription = "${tvShow.name} poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(16.dp))

        // Title
        Text(
            text = tvShow.name ?: tvShow.originalName.orEmpty(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        // First Air Date & Status
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "First Air: ${tvShow.firstAirDate.orEmpty()}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Status: ${tvShow.status.orEmpty()}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(8.dp))

        // Genres
        if (!tvShow.genres.isNullOrEmpty()) {
            val genreNames = tvShow.genres.filterNotNull().joinToString { it?.name.orEmpty() }
            Text(
                text = "Genres: $genreNames",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(12.dp))

        // Rating
        Text(
            text = "Rating: ${String.format("%.1f", tvShow.voteAverage ?: 0.0)} / 10",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(16.dp))

        // Overview
        Text(
            text = tvShow.overview.orEmpty(),
            style = MaterialTheme.typography.bodyLarge
        )
    }

}


@AppPreview
@Composable
private fun TvDetailsScreenPreview() {

    val dummyTvShow = TvShowDetails(
        backdropPath = "/bzoZjhbpriBT2N5kwgK0weUfVOX.jpg",
        createdBy = listOf(
            TvShowDetails.CreatedBy(
                creditId = "52e682cf9251415f28007e43",
                gender = 2,
                id = 66633,
                name = "Vince Gilligan",
                profilePath = "/uFh3OrBvkwKSU3N5y0XnXOhqBJz.jpg"
            )
        ),
        episodeRunTime = listOf(47),
        firstAirDate = "2008-01-20",
        genres = listOf(
            Genre(id = 18, name = "Drama"),
            Genre(id = 80, name = "Crime")
        ),
        homepage = "http://www.amc.com/shows/breaking-bad",
        id = 1396,
        inProduction = false,
        languages = listOf("en"),
        lastAirDate = "2013-09-29",
        lastEpisodeToAir = TvShowDetails.LastEpisodeToAir(
            airDate = "2013-09-29",
            episodeNumber = 16,
            id = 62161,
            name = "Felina",
            overview = "The series finale: Walter White returns to Albuquerque to tie up loose ends.",
            productionCode = "5AGH16",
            seasonNumber = 5,
            showId = 1396,
            stillPath = "/r3z70vunihrAkjILQKWHX0G2xzO.jpg",
            voteAverage = 9.7,
            voteCount = 220
        ),
        name = "Breaking Bad",
        networks = listOf(
            TvShowDetails.Network(
                id = 174,
                logoPath = "/alqLicR1ZMHMaZGP3xRQxn9sq7p.png",
                name = "AMC",
                originCountry = "US"
            )
        ),
        numberOfEpisodes = 62,
        numberOfSeasons = 5,
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalName = "Breaking Bad",
        overview = "When Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given only two years to live, he decides to risk everything by entering the meth business to secure his family's future.",
        popularity = 200.5,
        posterPath = "/ggFHVNu6YYI5L9pCfOacjizRGt.jpg",
        productionCompanies = listOf(
            TvShowDetails.ProductionCompany(
                id = 11073,
                logoPath = "/aCbASRcI1MI7DXjPbSW9Fcv9pvF.png",
                name = "High Bridge Entertainment",
                originCountry = "US"
            )
        ),
        seasons = listOf(
            TvShowDetails.Season(
                airDate = "2008-01-20",
                episodeCount = 7,
                id = 3572,
                name = "Season 1",
                overview = "Walter White’s transformation begins.",
                posterPath = "/1yeVJox3rjo2jBKrrihIMj7uoS9.jpg",
                seasonNumber = 1
            ),
            TvShowDetails.Season(
                airDate = "2009-03-08",
                episodeCount = 13,
                id = 3573,
                name = "Season 2",
                overview = "The empire grows as Walt dives deeper.",
                posterPath = "/e3oGYpoTUhOFK0BJfloru5ZmGV.jpg",
                seasonNumber = 2
            )
        ),
        status = "Ended",
        type = "Scripted",
        voteAverage = 8.9,
        voteCount = 14000
    )


    AppPreviewContainer {
        TvDetailsScreenUI(
            state = TvDetailsScreenUIState(
                tvShowDetails = dummyTvShow
            ),
            processEvent = {},
            modifier = Modifier,
        )
    }

}
