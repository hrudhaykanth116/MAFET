package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import com.hrudhaykanth116.todo.domain.model.TASK_CATEGORY_DEFAULT_NAME
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoTaskUseCase @Inject constructor(
    private val todoRepository: ITodoRepository,
    private val uniqueIdGenerator: UniqueIdGenerator,
) {

    suspend operator fun invoke(
        createOrUpdateTodoDomainModel: CreateOrUpdateTodoDomainModel,
    ): DomainState<CreateOrUpdateTodoDomainModel> {

        val stateAfterValidation = createOrUpdateTodoDomainModel.getStateAfterValidation()

        if (stateAfterValidation.containsError()) {
            return DomainState.LoadedDomainState(stateAfterValidation)
        } else {
            val params = CreateTodoParams(
                id = createOrUpdateTodoDomainModel.id ?: uniqueIdGenerator.getUniqueId(),
                title = stateAfterValidation.title.trim(),
                description = stateAfterValidation.description.trim(),
                category = createOrUpdateTodoDomainModel.category.replaceIfBlank(
                    TASK_CATEGORY_DEFAULT_NAME
                ).trim(),
                priority = createOrUpdateTodoDomainModel.priority,
                targetTime = createOrUpdateTodoDomainModel.targetTime,
            )
            val result: DataResult<String> = todoRepository.createTodoTask(params)

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