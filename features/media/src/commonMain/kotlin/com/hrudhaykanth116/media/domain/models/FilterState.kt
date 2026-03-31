package com.hrudhaykanth116.media.domain.models

data class FilterState(
    val orientation: OrientationType = OrientationType.ALL,
    val color: ColorFilter = ColorFilter.ALL,
    val size: SizeFilter = SizeFilter.ALL
) {
    fun hasActiveFilters(mediaType: MediaType): Boolean {
        return when (mediaType) {
            MediaType.PHOTOS -> {
                orientation != OrientationType.ALL ||
                        color != ColorFilter.ALL ||
                        size != SizeFilter.ALL
            }
            MediaType.VIDEOS -> {
                orientation != OrientationType.ALL
            }
        }
    }

    fun reset(): FilterState {
        return FilterState()
    }
}
