package com.hrudhaykanth116.tv.ui.preview

import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.tv.domain.models.TvCategory
import com.hrudhaykanth116.tv.ui.screens.all.TvShowCategoryUi
import com.hrudhaykanth116.tv.ui.screens.all.TvShowUi
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.image_error_holder
import mafet.core_ui.generated.resources.img_newyork
import org.jetbrains.compose.resources.DrawableResource

object TvPreviewData {

    fun sampleTvShow(
        id: Int = 1,
        name: String = "Sample TV Show",
        posterImage: DrawableResource = Res.drawable.image_error_holder,
        rating: Double = 8.5
    ): TvShowUi {
        return TvShowUi(
            id = id,
            name = name,
            posterImage = ImageHolder.LocalDrawableResource(posterImage),
            rating = rating
        )
    }

    fun sampleTvShows(
        count: Int = 6,
        posterImage: DrawableResource = Res.drawable.image_error_holder
    ): List<TvShowUi> {
        return List(count) { index ->
            sampleTvShow(
                id = index + 1,
                name = "Show ${index + 1}",
                posterImage = posterImage,
                rating = (7.0 + (index % 3) * 0.5)
            )
        }
    }

    fun sampleCategory(
        category: TvCategory = TvCategory.POPULAR,
        showCount: Int = 6,
        posterImage: DrawableResource = Res.drawable.image_error_holder
    ): TvShowCategoryUi {
        return TvShowCategoryUi(
            category = category,
            title = category.displayName,
            shows = sampleTvShows(showCount, posterImage)
        )
    }

    fun popularShows(
        count: Int = 6,
        posterImage: DrawableResource = Res.drawable.image_error_holder
    ): List<TvShowUi> {
        return List(count) { index ->
            sampleTvShow(
                id = 100 + index,
                name = listOf(
                    "Breaking Bad",
                    "Game of Thrones",
                    "The Last of Us",
                    "Stranger Things",
                    "The Crown",
                    "Wednesday"
                ).getOrElse(index) { "Popular Show ${index + 1}" },
                posterImage = posterImage,
                rating = (8.5 + (index % 3) * 0.3)
            )
        }
    }

    fun trendingShows(
        count: Int = 6,
        posterImage: DrawableResource = Res.drawable.image_error_holder
    ): List<TvShowUi> {
        return List(count) { index ->
            sampleTvShow(
                id = 200 + index,
                name = listOf(
                    "House of the Dragon",
                    "The Bear",
                    "Succession",
                    "The White Lotus",
                    "Severance",
                    "Wednesday"
                ).getOrElse(index) { "Trending Show ${index + 1}" },
                posterImage = posterImage,
                rating = (8.2 + (index % 3) * 0.4)
            )
        }
    }

    fun airingTodayShows(
        count: Int = 6,
        posterImage: DrawableResource = Res.drawable.image_error_holder
    ): List<TvShowUi> {
        return List(count) { index ->
            sampleTvShow(
                id = 300 + index,
                name = listOf(
                    "The Daily Show",
                    "Late Night",
                    "Morning News",
                    "Talk Show Live",
                    "Evening Prime",
                    "Night Watch"
                ).getOrElse(index) { "Airing Show ${index + 1}" },
                posterImage = posterImage,
                rating = (7.5 + (index % 3) * 0.3)
            )
        }
    }

    fun topRatedShows(
        count: Int = 6,
        posterImage: DrawableResource = Res.drawable.image_error_holder
    ): List<TvShowUi> {
        return List(count) { index ->
            sampleTvShow(
                id = 400 + index,
                name = listOf(
                    "The Sopranos",
                    "The Wire",
                    "Breaking Bad",
                    "Better Call Saul",
                    "Chernobyl",
                    "Band of Brothers"
                ).getOrElse(index) { "Top Rated Show ${index + 1}" },
                posterImage = posterImage,
                rating = (9.0 + (index % 2) * 0.2)
            )
        }
    }

    fun allCategories(
        posterImage: DrawableResource = Res.drawable.img_newyork
    ): List<TvShowCategoryUi> {
        return listOf(
            TvShowCategoryUi(
                category = TvCategory.POPULAR,
                title = TvCategory.POPULAR.displayName,
                shows = popularShows(posterImage = posterImage)
            ),
            TvShowCategoryUi(
                category = TvCategory.TRENDING,
                title = TvCategory.TRENDING.displayName,
                shows = trendingShows(posterImage = posterImage)
            ),
            TvShowCategoryUi(
                category = TvCategory.AIRING_TODAY,
                title = TvCategory.AIRING_TODAY.displayName,
                shows = airingTodayShows(posterImage = posterImage)
            ),
            TvShowCategoryUi(
                category = TvCategory.TOP_RATED,
                title = TvCategory.TOP_RATED.displayName,
                shows = topRatedShows(posterImage = posterImage)
            )
        )
    }
}
