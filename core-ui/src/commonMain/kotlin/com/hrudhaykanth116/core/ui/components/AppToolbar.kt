package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.preview.MyPreview
import com.hrudhaykanth116.core.ui.platform.ssp
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    text: String,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {
        AppClickableIcon(
            resource = Res.drawable.ic_back,
            onClick = onBackClicked,
            iconColor = Color.White
        )
    },
    actions: @Composable RowScope.() -> Unit = {},
) {

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.ssp,
                    color = Color.White
                )
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = WindowInsets(0, 0, 0, 0)
    )
}

@MyPreview
@Composable
fun AppToolbarPreview() {
    AppPreviewContainer {
        AppToolbar(
            text = "App toolbar preview that is large to test"
        )
    }
}