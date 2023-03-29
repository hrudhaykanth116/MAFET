package com.hrudhaykanth116.mafet.account.navigation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.mafet.common.ui.components.AppFormButton

@Composable
fun AccountScreen(onLoggedOut: (() -> Unit)? = null) {
    
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(start = 8.dp)
    ) {
        AppFormButton(btnText = "Logout"){
            onLoggedOut?.invoke()
        }
    }
    
}