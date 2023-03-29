package com.hrudhaykanth116.mafet.account.navigation.ui.models

sealed class AccountNavScreen(val route: String){
    object AccountScreen: AccountNavScreen("account_screen")
}