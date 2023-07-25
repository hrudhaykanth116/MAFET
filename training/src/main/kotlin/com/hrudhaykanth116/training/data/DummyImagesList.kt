package com.hrudhaykanth116.training.data

private const val widthPxl = 3670
private const val heightPxl = 2462

const val SAMPLE_IMAGE_URL = "https://picsum.photos/id/20/$widthPxl/$heightPxl"

fun getRandomImage(
    id: String,
    width: Int = widthPxl,
    height: Int = heightPxl,
): String {
    return "https://picsum.photos/id/$id/$width/$height"
}