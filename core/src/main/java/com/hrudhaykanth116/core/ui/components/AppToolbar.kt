package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.ui.models.toImageHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    text: String,
    modifier: Modifier = Modifier,
    // backgroundColor: Color = Color.Red,
    // contentColor: Color = Color.Unspecified,
    onBackClicked: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {
        AppClickableIcon(
            resId = R.drawable.ic_back,
            onClick = onBackClicked
        )
    },
    actions: @Composable RowScope.() -> Unit = {},
) {

    TopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            // titleContentColor = contentColor,
        ),
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = WindowInsets(0, 0, 0, 0) // removes system bar insets if needed
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