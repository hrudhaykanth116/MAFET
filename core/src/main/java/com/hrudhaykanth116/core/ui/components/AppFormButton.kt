package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import com.hrudhaykanth116.core.common.utils.compose.modifier.gradientBackground

@Composable
fun AppFormButton(
    btnText: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {

    Button(
        onClick = {
            onClick?.invoke()
        },
        modifier = modifier,
        // colors = ButtonDefaults.buttonColors(
        //     containerColor = Color.Black
        // ),
    ) {
        Text(
            text = btnText,
            style = MaterialTheme.typography.titleLarge,
        )
    }

}

@Composable
@Preview
fun AppButtonPreview() {
    AppFormButton(btnText = "Click me") {

    }
}