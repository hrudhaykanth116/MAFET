package com.hrudhaykanth116.auth.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData

@Composable
fun PasswordTextField(
    inputValue: TextFieldValue,
    error: UIText?,
    onPasswordChanged: (TextFieldValue) -> Unit
) {
    AppInputText(
        textFieldData = TextFieldData(
            hint = "Enter your password",
            inputType = InputType.PwdInputType,
            inputValue = inputValue,
            error = error?.getText()
        )
    ) { onPasswordChanged(it) }
}