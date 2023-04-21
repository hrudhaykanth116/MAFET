package com.hrudhaykanth116.todo.data.dummydata

import com.hrudhaykanth116.todo.domain.model.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

object DummyTodoList {

    val todoList: List<ToDoTaskUIState>
        get() {
            val list = mutableListOf<ToDoTaskUIState>()
            for (i in 1L..20) {
                list.add(
                    ToDoTaskUIState(
                        TodoUIModel(
                            i, "Task ${i}", "Some dummy information for Task ${i}"
                        ),
                    ),
                )
            }
            return list
        }

}