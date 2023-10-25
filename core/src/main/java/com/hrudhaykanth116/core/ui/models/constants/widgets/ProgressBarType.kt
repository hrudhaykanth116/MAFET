package com.hrudhaykanth116.core.ui.models.constants.widgets

import androidx.compose.ui.graphics.Color

sealed interface ProgressBarType{
    data class Circular(val color: Color = Color.Unspecified): ProgressBarType
}