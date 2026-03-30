package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.platform.ssp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppFormButton(
    btnText: UIText,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {

    Button(
        onClick = {
            onClick?.invoke()
        },
        enabled = enabled,
        modifier = modifier.height(54.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF10B981),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFE5E7EB),
            disabledContentColor = Color(0xFF9CA3AF)
        ),
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        )
    ) {
        AppText(
            uiText = btnText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.ssp,
                color = Color.White
            ),
        )
    }

}

@Composable
@Preview
fun AppButtonPreview() {
    AppFormButton(btnText = "Click me".toUIText()) {

    }
}