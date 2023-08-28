package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.common.utils.extensions.HandleEffect
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.mafet.home.models.HomeScreenNavigationHandler
import com.hrudhaykanth116.mafet.home.models.HomeScreenEffect
import com.hrudhaykanth116.mafet.home.models.HomeScreenEvent

@Composable
fun HomeScreen(
    name: String? = "",
    viewModel: HomeViewModel = hiltViewModel(),
    homeScreenNavigationHandler: HomeScreenNavigationHandler,
) {

    HandleEffect(viewModel = viewModel) { effect: HomeScreenEffect ->
        when (effect) {
            HomeScreenEffect.OnLogout -> {
                homeScreenNavigationHandler.onAccountClicked.invoke()
            }
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hi $name")
        Spacer(modifier = Modifier.height(30.dp))
        AppFormButton(
            onClick = { homeScreenNavigationHandler.onTodoClicked.invoke() },
            btnText = "Todo"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppFormButton(btnText = "Logout") {
            viewModel.processEvent(HomeScreenEvent.LogOut)
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppFormButton(btnText = "Weather") {
            homeScreenNavigationHandler.onWeatherClicked()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview(
    name: String = "Hrudhay",
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Hi $name \n Here are the features currently available.")
        Spacer(modifier = Modifier.height(30.dp))
        AppFormButton(
            btnText = "Todo"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppFormButton(
            btnText = "Account"
        )
    }
}