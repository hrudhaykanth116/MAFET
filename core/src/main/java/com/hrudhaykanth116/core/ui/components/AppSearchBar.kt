package com.hrudhaykanth116.core.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.models.toImageHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    text: String,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    placeHolderText: String? = null,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onCancelled: () -> Unit = {},
    onExpandedChange: (Boolean) -> Unit = {},
) {

    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = text,
                onQueryChange = onTextChange,
                onSearch = {
                    // String is ignored here to be derived from state directly.
                    onSearch()
                },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = if (placeHolderText != null) {
                    {
                        Text(text = placeHolderText)
                    }
                } else null,
                leadingIcon = {

                    if(expanded){
                        AppClickableIcon(
                            resId = R.drawable.ic_back,
                            onClick = {
                                onCancelled()
                            },
                            iconColor = LocalContentColor.current
                        )
                    }else{
                        AppClickableIcon(
                            resId = R.drawable.ic_search,
                            onClick = onSearch,
                            iconColor = LocalContentColor.current
                        )
                    }
                },
                trailingIcon = if (expanded) {
                    {
                        AppClickableIcon(
                            resId = R.drawable.ic_search,
                            onClick = {
                                onSearch()
                            },
                            iconColor = LocalContentColor.current
                        )
                    }
                } else null
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {

        // Text(text = "Dummy text")


    }

}

@Preview
@Composable
fun AppSearchBarPreview() {
    AppSearchBar(
        text = "",
        onTextChange = {},
        placeHolderText = "Start typing",
        onSearch = {}
    )
}