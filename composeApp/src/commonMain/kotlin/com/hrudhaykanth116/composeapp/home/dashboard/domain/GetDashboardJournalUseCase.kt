package com.hrudhaykanth116.composeapp.home.dashboard.domain

import com.hrudhaykanth116.composeapp.home.dashboard.models.JournalSummary
import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.journal.domain.use_cases.ObserveEntriesUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class GetDashboardJournalUseCase(
    private val observeEntriesUseCase: ObserveEntriesUseCase,
    private val timeProvider: TimeProvider,
) {
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(): JournalSummary? {
        val entries = observeEntriesUseCase().firstOrNull().orEmpty()
        if (entries.isEmpty()) return null

        val tz = TimeZone.currentSystemDefault()
        val startOfToday = Instant.fromEpochMilliseconds(timeProvider.currentTimeMillis())
            .toLocalDateTime(tz)
            .date
            .atStartOfDayIn(tz)
            .toEpochMilliseconds()

        val todays = entries.firstOrNull { it.createdAt >= startOfToday } ?: return null

        return JournalSummary(
            id = todays.id,
            title = todays.title.ifBlank { "Untitled" },
            bodyPreview = todays.body.take(80).let {
                if (todays.body.length > 80) "$it…" else it
            },
            moodEmoji = moodToEmoji(todays.mood),
            dateLabel = "Today",
        )
    }

    private fun moodToEmoji(mood: Int): String = when (mood) {
        1 -> "\uD83D\uDE1E"
        2 -> "\uD83D\uDE14"
        3 -> "\uD83D\uDE10"
        4 -> "\uD83D\uDE0A"
        5 -> "\uD83D\uDE04"
        else -> "\uD83D\uDE10"
    }
}
