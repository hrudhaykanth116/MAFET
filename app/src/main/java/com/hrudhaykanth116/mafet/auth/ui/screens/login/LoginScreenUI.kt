package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens.DEFAULT_PADDING
import com.hrudhaykanth116.core.common.utils.color.ColorParser
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.compose.modifier.gradientBackground
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.RoundedImage
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.ImageParams
import com.hrudhaykanth116.mafet.R
import com.hrudhaykanth116.mafet.auth.domain.models.login.LoginScreenCallBacks
import com.hrudhaykanth116.mafet.auth.domain.models.login.LoginScreenState
import com.hrudhaykanth116.mafet.auth.ui.components.EmailTextField
import com.hrudhaykanth116.mafet.auth.ui.components.PasswordTextField
import com.hrudhaykanth116.core.R as CoreR

@Composable
fun LoginScreenUI(
    state: LoginScreenState,
    loginScreenCallBacks: LoginScreenCallBacks,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.gradientBackground(listOf(Color.Cyan, Color.Blue))
    ) {
        CenteredColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = DEFAULT_PADDING)
        ) {
            RoundedImage(
                imageParams = ImageParams(
                    image = CoreR.drawable.ic_genie,

                    ),
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(color = ColorParser.parseHexCode(0XFFf1a2fa))
                    .border(2.dp, Color.Green, CircleShape)
                    .padding(20.dp)
                // .background(color = Color.Green)
                // .border(2.dp, Color.Green, CircleShape),
            )
            VerticalSpacer(24.dp)
            AppText(uiText = "Login".toUIText(), style = MaterialTheme.typography.titleLarge)
            VerticalSpacer(16.dp)
            EmailTextField(
                state.loginEmail,
                state.emailError,
                loginScreenCallBacks.onEmailChanged,
            )
            VerticalSpacer(8.dp)
            PasswordTextField(
                state.loginPassword,
                state.passwordError,
                loginScreenCallBacks.onPasswordChanged
            )
            VerticalSpacer(24.dp)
            AppFormButton(
                "Login",
                modifier = Modifier.fillMaxWidth(),
                onClick = loginScreenCallBacks.onLoginBtnClicked,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Don't have an account ?", style = MaterialTheme.typography.bodyMedium)
            HorizontalSpacer(width = 8.dp)
            Text(
                text = "Sign up",
                modifier = Modifier.clickable {
                    loginScreenCallBacks.onSignUpBtnClicked
                },
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        VerticalSpacer(height = DEFAULT_PADDING)
    }
}

@MyPreview
@Composable
fun LoginScreenUIPreview() {
    LoginScreenUI(
        state = LoginScreenState(

        ),
        loginScreenCallBacks = LoginScreenCallBacks()
    )
}