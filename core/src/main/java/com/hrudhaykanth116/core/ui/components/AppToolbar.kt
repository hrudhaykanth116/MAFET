package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.ui.models.toImageHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Red,
    contentColor: Color = Color.White,
    onBackClicked: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {
        AppClickableIcon(
            imageHolder = R.drawable.ic_back.toImageHolder(),
            iconColor = Color.White,
            onClick = onBackClicked
        )
    },
    actions: @Composable RowScope.() -> Unit = {},
) {

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor,
        ),
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@MyPreview
@Composable
fun AppToolbarPreview() {
    AppToolbar(
        text = "App toolbar preview that is large to test"
    )
}