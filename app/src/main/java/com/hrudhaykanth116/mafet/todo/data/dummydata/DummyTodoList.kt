package com.hrudhaykanth116.mafet.todo.data.dummydata

import com.hrudhaykanth116.mafet.home.MainNavScreen
import com.hrudhaykanth116.mafet.todo.ui.models.ToDoTaskUIState

object DummyTodoList {

    val todoList: List<ToDoTaskUIState>
        get() {
            val list = mutableListOf<ToDoTaskUIState>()
            for (i in 1L..20) {
                // list.add(
                //     ToDoTaskUIState(i, "Task ${i}", "Some dummy information for Task ${i}", true),
                // )
            }
            return list
        }

}