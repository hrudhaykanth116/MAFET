package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.model.TodoUIModel
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoTaskUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        createTodoUIState: CreateTodoUIState,
    ): UIState<CreateTodoUIState>{
        // todoRepository.createTodoTask(
        //     todoUIModel.id,
        //     todoUIModel.title,
        //     todoUIModel.description,
        //     todoUIModel.category,
        //     todoUIModel.completed,
        // )

        delay(2000)

        if (createTodoUIState.title.text.isEmpty()) {
            return UIState.ErrorUIState(
                text = "Title cannot be empty".toUIText(),
                createTodoUIState.copy()
            )
        }else if(createTodoUIState.description.text.isEmpty()){
            return UIState.ErrorUIState(
                text = "Description cannot be empty".toUIText(),
                createTodoUIState.copy()
            )
        }else{
            return UIState.LoadedUIState(
                createTodoUIState.copy(
                    isSubmitted = true
                )
            )
        }
    }
}