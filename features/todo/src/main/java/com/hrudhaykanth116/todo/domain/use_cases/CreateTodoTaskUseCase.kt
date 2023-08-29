package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.model.TASK_CATEGORY_DEFAULT_NAME
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoTaskUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
    private val uniqueIdGenerator: UniqueIdGenerator,
) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        createOrUpdateTodoDomainModel: CreateOrUpdateTodoDomainModel,
    ): DomainState<CreateOrUpdateTodoDomainModel> {

        val stateAfterValidation = createOrUpdateTodoDomainModel.getStateAfterValidation()

        if (stateAfterValidation.containsError()) {
            return DomainState.LoadedDomainState(stateAfterValidation)
        } else {
            val result: DataResult<Unit> = todoRepository.createTodoTask(
                id = createOrUpdateTodoDomainModel.id ?: uniqueIdGenerator.getUniqueId(),
                title = stateAfterValidation.title.trim(),
                description = stateAfterValidation.description.trim(),
                category = createOrUpdateTodoDomainModel.category.replaceIfBlank(
                    TASK_CATEGORY_DEFAULT_NAME
                ).trim(),
            )

            return result.process(
                onSuccess = {
                    onCreated(stateAfterValidation)
                },
                onError = {
                    onCreationError(it, stateAfterValidation)
                }
            )
        }
    }

    private fun onCreated(stateAfterValidation: CreateOrUpdateTodoDomainModel) =
        DomainState.LoadedDomainState(
            stateAfterValidation.copy(
                isSubmitted = true,
            )
        )

    private fun onCreationError(
        result: DataResult.Error,
        stateAfterValidation: CreateOrUpdateTodoDomainModel,
    ) = DomainState.ErrorDomainState(
        result.domainMessage ?: ErrorState.Unknown,
        stateAfterValidation
    )
}