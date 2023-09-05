package com.hrudhaykanth116.auth.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.auth.domain.models.signup.SignUpFormState
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData

@Composable
fun UserNameTextField(
    state: SignUpFormState,
    onReEnterPasswordChange: (TextFieldValue) -> Unit,
) {
    AppInputText(
        textFieldData = TextFieldData(
            hint = "Username",
            inputType = InputType.RegularInputType,
            inputValue = state.userName,
            error = state.userNameError?.getText()
        )
    ) { onReEnterPasswordChange(it) }
}