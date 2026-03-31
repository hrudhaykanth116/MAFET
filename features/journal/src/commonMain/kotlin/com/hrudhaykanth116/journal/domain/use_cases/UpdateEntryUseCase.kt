package com.hrudhaykanth116.journal.domain.use_cases

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.journal.domain.model.JournalEntry
import com.hrudhaykanth116.journal.domain.repository.IJournalRepository

class UpdateEntryUseCase(
    private val repository: IJournalRepository
) {
    suspend operator fun invoke(entry: JournalEntry): DomainResult<Unit> {
        if (entry.id.isBlank()) {
            return DomainResult.Error(DomainError.Validation("Entry ID is required"))
        }
        if (entry.title.isBlank()) {
            return DomainResult.Error(DomainError.Validation("Title is required"))
        }
        if (entry.body.isBlank()) {
            return DomainResult.Error(DomainError.Validation("Content is required"))
        }
        return repository.updateEntry(entry)
    }
}
