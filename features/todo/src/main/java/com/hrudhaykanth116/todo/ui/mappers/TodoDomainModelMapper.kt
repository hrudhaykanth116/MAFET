package com.hrudhaykanth116.todo.ui.mappers

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoDomainModelMapper @Inject constructor(){

    fun mapToUIModel(todoModel: TodoModel?): TodoUIModel {

        todoModel ?: return TodoUIModel()

        return TodoUIModel(
            id = todoModel.id,
            title = TextFieldValue(todoModel.title),
            description = TextFieldValue(todoModel.description),
            category = TextFieldValue(todoModel.category),
            priority = todoModel.priority,
            targetTime = TextFieldValue(DateTimeUtils.DAY_DATE_FORMAT),
        )

    }

}

fun TodoModel?.toUIModel(): TodoUIModel {

    this ?: return TodoUIModel()

    return TodoUIModel(
        id = id,
        title = TextFieldValue(title),
        description = TextFieldValue(description),
        category = TextFieldValue(category),
        priority = priority,
        targetTime = TextFieldValue(DateTimeUtils.DAY_DATE_FORMAT),
    )

}

// TODO: Wrong. Do something
fun TodoModel.toState(): ToDoTaskUIState{
    return ToDoTaskUIState(
        data = this.toUIModel(),
    )
}