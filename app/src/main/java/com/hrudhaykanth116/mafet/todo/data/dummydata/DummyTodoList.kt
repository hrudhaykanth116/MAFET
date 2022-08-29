package com.hrudhaykanth116.mafet.todo.data.dummydata

import com.hrudhaykanth116.mafet.todo.ui.data_models.ToDoTaskUIState

object DummyTodoList {

    val todoList: List<ToDoTaskUIState>
        get() {
            val list = mutableListOf<ToDoTaskUIState>()
            for (i in 1L..20) {
                list.add(
                    ToDoTaskUIState(i, "Task ${i}", "Some dummy information for Task ${i}", true),
                )
            }
            return list
            // return listOf(
            //     ToDoTask(1, "test1", true),
            //     ToDoTask(2, "test2", true),
            //     ToDoTask(3, "test3", true),
            //     ToDoTask(4, "test4", false),
            //     ToDoTask(5, "test5", true),
            //     ToDoTask(6, "test6", false),
            //     ToDoTask(7, "test7", true),
            //     ToDoTask(8, "test8", false),
            //     ToDoTask(9, "test9", false),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            //     ToDoTask(10, "test10", true),
            // )
        }

}