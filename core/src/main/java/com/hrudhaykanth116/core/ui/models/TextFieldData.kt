package com.hrudhaykanth116.core.ui.models

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.models.InputType

data class TextFieldData(
    val inputValue: TextFieldValue,
    val inputType: InputType,
    val label: String? = null,
    val hint: String? = null,
    val error: String? = null,
)

