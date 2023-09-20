package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.models.toImageHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            query = text,
            onQueryChange = onTextChange,
            onSearch = {
                // String is ignored here to be derived from state directly.
                onSearch()
            },
            active = false,
            onActiveChange = {

            }
        ) {

        }
        AppClickableIcon(
            imageHolder = R.drawable.ic_search.toImageHolder(),
            onClick = onSearch,
            iconColor = LocalContentColor.current
        )
    }

}

@Preview
@Composable
fun AppSearchBarPreview() {
    AppSearchBar(text = "Search criteria", onTextChange = {}, onSearch = {})
}