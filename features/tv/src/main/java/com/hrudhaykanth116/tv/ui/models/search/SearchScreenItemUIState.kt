package com.hrudhaykanth116.tv.ui.models.search

import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class SearchScreenItemUIState(
    val id: Int,
    val name: UIText,
    val image: ImageHolder?,
    val isMyTvList: Boolean,
)
