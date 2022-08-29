package com.hrudhaykanth116.mafet.auth.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.mafet.auth.ui.navigation.AuthNavigation
import com.hrudhaykanth116.mafet.common.ui.components.AppFormInputText
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AuthNavigation()
            }
        }
    }

    companion object {

        public fun start(context: Context) {
            context.startActivity(
                Intent(context, LoginActivity::class.java)
            )
        }
    }
}

@Composable
fun LoginScreen(navigateToHomeScreen: (String) -> Unit) {

    var nameInputValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    CenteredColumn {
        AppFormInputText(nameInputValue, label = "Name") { nameInputValue = it }
        Button(onClick = {
            navigateToHomeScreen(nameInputValue.text)
        }) {
            Text(text = "Click me")
        }
    }
}