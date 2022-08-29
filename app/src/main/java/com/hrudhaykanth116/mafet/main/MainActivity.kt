package com.hrudhaykanth116.mafet.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.mafet.auth.data.local.shared_preferences.AuthPreffs
import com.hrudhaykanth116.mafet.auth.ui.navigation.AuthNavigation
import com.hrudhaykanth116.mafet.common.ui.components.AppButton
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.home.HomeNavigation
import com.hrudhaykanth116.mafet.theme.AppTheme
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                if (AuthPreffs.isLoggedIn) {
                    HomeNavigation("hrudhay")
                }else{
                    AuthNavigation()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onTodoClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    CenteredColumn {
        AppButton(onClick = onTodoClicked, btnText = "Todo")
        Spacer(modifier = Modifier.height(8.dp))
        AppButton(onClick = onLogoutClicked, btnText = "Logout")
    }
}
