package com.hrudhaykanth116.journal.ui.mappers

import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.journal.domain.model.JournalEntry
import com.hrudhaykanth116.journal.ui.models.JournalEntryUIModel
import kotlinx.collections.immutable.toImmutableList

class JournalUIMapper(
    private val dateTimeUtils: DateTimeUtils
) {
    fun mapToUIModel(entry: JournalEntry): JournalEntryUIModel {
        return JournalEntryUIModel(
            id = entry.id,
            title = entry.title,
            bodyPreview = entry.body.take(100).let {
                if (entry.body.length > 100) "$it..." else it
            },
            mood = entry.mood,
            moodEmoji = getMoodEmoji(entry.mood),
            tags = entry.tags.toImmutableList(),
            formattedDate = dateTimeUtils.getFormattedDateTime(
                entry.createdAt,
                DateTimeUtils.DAY_DATE_FORMAT
            ) ?: "",
            createdAt = entry.createdAt
        )
    }

    fun mapToUIModels(entries: List<JournalEntry>): List<JournalEntryUIModel> {
        return entries.map { mapToUIModel(it) }
    }

    private fun getMoodEmoji(mood: Int): String {
        return when (mood) {
            1 -> "\uD83D\uDE1E"
            2 -> "\uD83D\uDE14"
            3 -> "\uD83D\uDE10"
            4 -> "\uD83D\uDE0A"
            5 -> "\uD83D\uDE04"
            else -> "\uD83D\uDE10"
        }
    }
}
