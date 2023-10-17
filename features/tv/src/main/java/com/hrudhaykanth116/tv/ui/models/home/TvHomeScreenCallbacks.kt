package com.hrudhaykanth116.tv.ui.models.home

data class TvHomeScreenCallbacks(
    val onAddNewClicked: () -> Unit,
    val onUpdateTvCloseRequest: () -> Unit,
    val onTvListItemClicked: (myTv: MyTvUIState) -> Unit,
)
