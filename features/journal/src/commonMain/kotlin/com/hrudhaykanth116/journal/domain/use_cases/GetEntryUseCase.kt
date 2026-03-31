package com.hrudhaykanth116.journal.domain.use_cases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.journal.domain.model.JournalEntry
import com.hrudhaykanth116.journal.domain.repository.IJournalRepository

class GetEntryUseCase(
    private val repository: IJournalRepository
) {
    suspend operator fun invoke(id: String): DomainResult<JournalEntry> {
        return repository.getEntry(id)
    }
}
