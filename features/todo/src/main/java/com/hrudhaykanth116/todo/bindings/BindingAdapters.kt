package com.hrudhaykanth116.todo.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.todo.adapters.ToDoListAdapter
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

/**
 * [BindingAdapter]s for the [ToDoTaskUIState]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<ToDoTaskUIState>?) {
    items?.let {
        (listView.adapter as com.hrudhaykanth116.todo.adapters.ToDoListAdapter).updateList(items)
    }
}