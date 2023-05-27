package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoTaskUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        createOrUpdateTodoDomainModel: CreateOrUpdateTodoDomainModel,
    ): DomainState<CreateOrUpdateTodoDomainModel> {

        val stateAfterValidation = createOrUpdateTodoDomainModel.getStateAfterValidation()

        if (stateAfterValidation.containsError()) {
            return DomainState.LoadedDomainState(stateAfterValidation)
        } else {
            val result = todoRepository.createTodoTask(
                id = System.currentTimeMillis().toString(),
                title = stateAfterValidation.title,
                description = stateAfterValidation.description,
                category = TaskCategory.toId(createOrUpdateTodoDomainModel.category)
                    ?: TaskCategory.GENERAL.id,
            )

            return when (result) {
                is DataResult.Error -> {
                    DomainState.ErrorDomainState(
                        result.domainMessage ?: ErrorState.Unknown,
                        stateAfterValidation
                    )
                }
                is DataResult.Success -> {
                    DomainState.LoadedDomainState(
                        stateAfterValidation.copy(isSubmitted = true)
                    )
                }
            }
        }
    }
}