package com.hrudhaykanth116.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.95f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                keyboardController?.hide()
                onCancelled()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Close search",
                    tint = Color(0xFF3B82F6)
                )
            }

            TextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = placeHolderText ?: "",
                        color = Color(0xFF9CA3AF),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFF111827),
                    unfocusedTextColor = Color(0xFF111827),
                    cursorColor = Color(0xFF3B82F6)
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        onSearch()
                    }
                )
            )

            AnimatedVisibility(
                visible = text.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { onTextChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = Color(0xFF6B7280)
                    )
                }
            }
        }
    }

    // SearchBar(
    //     modifier = modifier,
    //     windowInsets = WindowInsets(0, 0, 0, 0),
    //     inputField = {
    //         SearchBarDefaults.InputField(
    //             query = text,
    //             onQueryChange = onTextChange,
    //             onSearch = {
    //                 // String is ignored here to be derived from state directly.
    //                 onSearch()
    //             },
    //             expanded = expanded,
    //             onExpandedChange = onExpandedChange,
    //             placeholder = if (placeHolderText != null) {
    //                 {
    //                     Text(text = placeHolderText)
    //                 }
    //             } else null,
    //             leadingIcon = {
    //
    //                 if(expanded){
    //                     AppClickableIcon(
    //                         resId = R.drawable.ic_back,
    //                         onClick = {
    //                             onCancelled()
    //                         },
    //                         iconColor = LocalContentColor.current
    //                     )
    //                 }else{
    //                     AppClickableIcon(
    //                         resId = R.drawable.ic_search,
    //                         onClick = onSearch,
    //                         iconColor = LocalContentColor.current
    //                     )
    //                 }
    //             },
    //             trailingIcon = if (expanded) {
    //                 {
    //                     AppClickableIcon(
    //                         resId = R.drawable.ic_search,
    //                         onClick = {
    //                             onSearch()
    //                         },
    //                         iconColor = LocalContentColor.current
    //                     )
    //                 }
    //             } else null
    //         )
    //     },
    //     expanded = expanded,
    //     onExpandedChange = onExpandedChange,
    // ) {
    //
    //     // Text(text = "Dummy text")
    //
    //
    // }

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