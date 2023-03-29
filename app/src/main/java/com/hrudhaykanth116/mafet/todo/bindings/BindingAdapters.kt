package com.hrudhaykanth116.mafet.todo.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.mafet.todo.adapters.ToDoListAdapter
import com.hrudhaykanth116.mafet.todo.ui.models.ToDoTaskUIState

/**
 * [BindingAdapter]s for the [ToDoTaskUIState]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<ToDoTaskUIState>?) {
    items?.let {
        (listView.adapter as ToDoListAdapter).updateList(items)
    }
}