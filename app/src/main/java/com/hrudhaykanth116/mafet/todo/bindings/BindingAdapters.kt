package com.hrudhaykanth116.mafet.todo.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.mafet.todo.adapters.ToDoListAdapter
import com.hrudhaykanth116.mafet.todo.data.models.ToDoTask

/**
 * [BindingAdapter]s for the [ToDoTask]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<ToDoTask>?) {
    items?.let {
        (listView.adapter as ToDoListAdapter).updateList(items)
    }
}