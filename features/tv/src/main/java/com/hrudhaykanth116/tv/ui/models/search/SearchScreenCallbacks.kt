package com.hrudhaykanth116.tv.ui.models.search

import androidx.compose.ui.text.input.TextFieldValue

class SearchScreenCallbacks(
    val onSearchTextChanged: (String) -> Unit,
    val onSearchIconClicked: () -> Unit,
)