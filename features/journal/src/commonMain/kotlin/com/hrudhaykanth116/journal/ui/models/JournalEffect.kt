package com.hrudhaykanth116.journal.ui.models

import com.hrudhaykanth116.core.ui.models.UIText

sealed interface JournalEffect {
    data class ShowToast(val message: UIText) : JournalEffect
}
