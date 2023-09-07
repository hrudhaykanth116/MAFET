package com.hrudhaykanth116.core.common.utils.compose

import androidx.compose.ui.text.input.TextFieldValue

fun TextFieldValue.isBlank(): Boolean {
    return text.isBlank()
}