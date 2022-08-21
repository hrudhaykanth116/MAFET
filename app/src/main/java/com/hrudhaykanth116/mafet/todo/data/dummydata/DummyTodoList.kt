package com.hrudhaykanth116.mafet.todo.data.dummydata

import com.hrudhaykanth116.mafet.todo.data.models.ToDoTask

object DummyTodoList {

    val todoList: List<ToDoTask>
        get() {
            val list = mutableListOf<ToDoTask>()
            for (i in 1L..20) {
                list.add(
                    ToDoTask(i, "Task ${i}", "Some dummy information for Task ${i}", true),
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