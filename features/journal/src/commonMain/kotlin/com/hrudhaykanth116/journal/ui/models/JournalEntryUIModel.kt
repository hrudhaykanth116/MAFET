package com.hrudhaykanth116.journal.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JournalEntryUIModel(
    val id: String,
    val title: String,
    val bodyPreview: String,
    val mood: Int,
    val moodEmoji: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val formattedDate: String,
    val createdAt: Long
)
