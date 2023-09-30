package com.hrudhaykanth116.mafet.account.navigation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
fun AccountScreen(onLoggedOut: (() -> Unit)? = null) {

    CenteredColumn(
    ) {
        AppText(uiText = "Preferences, Account deletion etc.. coming up".toUIText())
        AppFormButton(btnText = "Logout".toUIText()) {
            onLoggedOut?.invoke()
        }
    }

}