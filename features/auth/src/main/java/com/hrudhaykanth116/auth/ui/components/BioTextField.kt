package com.hrudhaykanth116.auth.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.auth.domain.models.signup.SignUpFormState
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData

@Composable
fun BioTextField(
    state: SignUpFormState,
    onReEnterPasswordChange: (TextFieldValue) -> Unit,
) {
    AppInputText(
        textFieldData = TextFieldData(
            hint = "A short bio",
            inputType = InputType.RegularInputType,
            inputValue = state.bio,
            error = state.bioError?.getText()
        )
    ) { onReEnterPasswordChange(it) }
}