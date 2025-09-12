package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import com.hrudhaykanth116.todo.domain.model.TodoModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoTaskUseCase @Inject constructor(
    private val todoRepository: ITodoRepository,
    private val uniqueIdGenerator: UniqueIdGenerator,
) {

    suspend operator fun invoke(
        todoModel: TodoModel,
    ): RepoResultWrapper<Unit> {
        //
        // val stateAfterValidation = createOrUpdateTodoDomainModel.getStateAfterValidation()
        //
        // if (stateAfterValidation.containsError()) {
        //     return DomainState.LoadedDomainState(stateAfterValidation)
        // } else {
        //     val params = CreateTodoParams(
        //         id = createOrUpdateTodoDomainModel.id ?: uniqueIdGenerator.getUniqueId(),
        //         title = stateAfterValidation.title.trim(),
        //         description = stateAfterValidation.description.trim(),
        //         category = createOrUpdateTodoDomainModel.category.replaceIfBlank(
        //             TASK_CATEGORY_DEFAULT_NAME
        //         ).trim(),
        //         priority = createOrUpdateTodoDomainModel.priority,
        //         targetTime = createOrUpdateTodoDomainModel.targetTime,
        //     )
        //     val result: DataResult<String> = todoRepository.createTodoTask(params)
        //
        //     return result.process(
        //         onSuccess = {
        //             onCreated(stateAfterValidation)
        //         },
        //         onError = {
        //             onCreationError(it, stateAfterValidation)
        //         }
        //     )
        // }


        val result: RepoResultWrapper<Unit> = todoRepository.createTodoTask(todoModel,
            id = uniqueIdGenerator.getUniqueId()
        )

        return result
    }

    // private fun onCreated(stateAfterValidation: CreateOrUpdateTodoDomainModel) =
    //     DomainState.LoadedDomainState(
    //         stateAfterValidation.copy(
    //             isSubmitted = true,
    //         )
    //     )
    //
    // private fun onCreationError(
    //     result: DataResult.Error,
    //     stateAfterValidation: CreateOrUpdateTodoDomainModel,
    // ) = DomainState.ErrorDomainState(
    //     result.domainMessage ?: ErrorState.Unknown,
    //     stateAfterValidation
    // )
}