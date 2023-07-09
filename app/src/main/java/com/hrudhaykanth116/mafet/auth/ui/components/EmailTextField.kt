package com.hrudhaykanth116.mafet.auth.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.components.AppFormInputText
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormState

@Composable
fun EmailTextField(
    inputValue: TextFieldValue,
    error: UIText?,
    onEmailChanged: (TextFieldValue) -> Unit
) {
    AppFormInputText(
        textFieldData = TextFieldData(
            hint = "Enter your email",
            inputType = InputType.EmailInputType,
            inputValue = inputValue,
            error = error?.getText()
        )
    ) { onEmailChanged(it) }
}