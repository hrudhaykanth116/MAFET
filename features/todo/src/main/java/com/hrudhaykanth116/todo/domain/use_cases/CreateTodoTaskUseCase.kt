package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.model.create.TodoUIState
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
        todoUIState: TodoUIState,
    ): UIState<TodoUIState>{
        // todoRepository.createTodoTask(
        //     todoUIModel.id,
        //     todoUIModel.title,
        //     todoUIModel.description,
        //     todoUIModel.category,
        //     todoUIModel.completed,
        // )

        delay(2000)

        if (todoUIState.title.text.isEmpty()) {
            return UIState.ErrorUIState(
                text = "Title cannot be empty".toUIText(),
                todoUIState.copy()
            )
        }else if(todoUIState.description.text.isEmpty()){
            return UIState.ErrorUIState(
                text = "Description cannot be empty".toUIText(),
                todoUIState.copy()
            )
        }else{
            return UIState.LoadedUIState(
                todoUIState.copy(
                    isSubmitted = true
                )
            )
        }
    }
}