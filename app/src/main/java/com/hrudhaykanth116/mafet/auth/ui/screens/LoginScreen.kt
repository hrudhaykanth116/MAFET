package com.hrudhaykanth116.mafet.auth.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.mafet.common.ui.components.AppFormButton
import com.hrudhaykanth116.mafet.common.ui.components.AppFormInputText
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn

@Composable
fun LoginScreen(
    navigateToHomeScreen: (String) -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {

    var nameInputValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    CenteredColumn {
        AppFormInputText(nameInputValue, label = "Name") { nameInputValue = it }
        AppFormButton("Login") {
            navigateToHomeScreen(nameInputValue.text)
        }
        AppFormButton("Sign up") {
            navigateToSignUpScreen()
        }




    }
}