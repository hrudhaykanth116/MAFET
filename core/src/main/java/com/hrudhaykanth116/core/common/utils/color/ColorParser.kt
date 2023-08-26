package com.hrudhaykanth116.core.common.utils.color

import androidx.compose.ui.graphics.Color

object ColorParser {

    fun parseHexCode(hexCode: Long): Color {
        return Color(hexCode);
    }

}