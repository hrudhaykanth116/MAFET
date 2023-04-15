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
import com.hrudhaykanth116.mafet.auth.data.repositories.AuthRepository
import com.hrudhaykanth116.mafet.auth.ui.navigation.AuthNavigation
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.home.HomeNavigation
import com.hrudhaykanth116.mafet.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                if (authRepository.isLoggedIn()) {
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
    com.hrudhaykanth116.core.ui.components.CenteredColumn {
        com.hrudhaykanth116.core.ui.components.AppFormButton(
            onClick = onTodoClicked,
            btnText = "Todo"
        )
        Spacer(modifier = Modifier.height(8.dp))
        com.hrudhaykanth116.core.ui.components.AppFormButton(
            onClick = onLogoutClicked,
            btnText = "Logout"
        )
    }
}
