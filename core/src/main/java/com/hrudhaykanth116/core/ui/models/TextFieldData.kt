package com.hrudhaykanth116.core.ui.models

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.models.InputType

data class TextFieldData(
    val inputValue: TextFieldValue = TextFieldValue(),
    val inputType: InputType = InputType.RegularInputType,
    val label: String? = null,
    val hint: String? = null,
    val error: String? = null,
)

