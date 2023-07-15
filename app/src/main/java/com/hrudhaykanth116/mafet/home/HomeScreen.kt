package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.common.utils.extensions.HandleEffect
import com.hrudhaykanth116.mafet.home.models.HomeScreenEffect
import com.hrudhaykanth116.mafet.home.models.HomeScreenEvent

@Composable
fun HomeScreen(
    name: String? = "",
    viewmodel: HomeViewModel = hiltViewModel(),
    onTodoClicked: (() -> Unit)? = null,
    onAccountClicked: (() -> Unit)? = null
) {

    HandleEffect(viewModel = viewmodel) { effect: HomeScreenEffect ->
        when (effect) {
            HomeScreenEffect.OnLogout -> {
                onAccountClicked?.invoke()
            }
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hi $name")
        Spacer(modifier = Modifier.height(30.dp))
        com.hrudhaykanth116.core.ui.components.AppFormButton(
            onClick = { onTodoClicked?.invoke() },
            btnText = "Todo"
        )
        Spacer(modifier = Modifier.height(8.dp))
        com.hrudhaykanth116.core.ui.components.AppFormButton(btnText = "Logout") {
            viewmodel.processEvent(HomeScreenEvent.LogOut)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview(
    name: String = "Hrudhay"
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Hi $name \n Here are the features currently available.")
        Spacer(modifier = Modifier.height(30.dp))
        com.hrudhaykanth116.core.ui.components.AppFormButton(
            btnText = "Todo"
        )
        Spacer(modifier = Modifier.height(8.dp))
        com.hrudhaykanth116.core.ui.components.AppFormButton(
            btnText = "Account"
        )
    }
}