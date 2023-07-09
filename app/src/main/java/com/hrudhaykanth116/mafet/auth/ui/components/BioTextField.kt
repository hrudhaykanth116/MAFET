package com.hrudhaykanth116.mafet.auth.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.components.AppFormInputText
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormState

@Composable
fun BioTextField(
    state: SignUpFormState,
    onReEnterPasswordChange: (TextFieldValue) -> Unit
) {
    AppFormInputText(
        textFieldData = TextFieldData(
            hint = "A short bio",
            inputType = InputType.RegularInputType,
            inputValue = state.bio,
            error = state.bioError?.getText()
        )
    ) { onReEnterPasswordChange(it) }
}