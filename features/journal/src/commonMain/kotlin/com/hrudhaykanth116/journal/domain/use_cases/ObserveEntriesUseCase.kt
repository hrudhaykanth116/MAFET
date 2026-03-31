package com.hrudhaykanth116.journal.domain.use_cases

import com.hrudhaykanth116.journal.domain.model.JournalEntry
import com.hrudhaykanth116.journal.domain.repository.IJournalRepository
import kotlinx.coroutines.flow.Flow

class ObserveEntriesUseCase(
    private val repository: IJournalRepository
) {
    operator fun invoke(search: String? = null): Flow<List<JournalEntry>> {
        return repository.observeEntries(search)
    }
}
