package com.hrudhaykanth116.journal.domain.use_cases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.journal.domain.repository.IJournalRepository

class DeleteEntryUseCase(
    private val repository: IJournalRepository
) {
    suspend operator fun invoke(id: String): DomainResult<Unit> {
        return repository.deleteEntry(id)
    }
}
