package com.hrudhaykanth116.tv.ui.screens.details

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.tv.domain.models.TvShowDetail


data class TvDetailsScreenUIState(
    // TODO: make non nullable
    val tvShowDetails: TvShowDetail? = null,
)