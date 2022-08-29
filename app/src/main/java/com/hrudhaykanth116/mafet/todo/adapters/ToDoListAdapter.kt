package com.hrudhaykanth116.mafet.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.mafet.databinding.TodoTaskListItemBinding
import com.hrudhaykanth116.mafet.todo.ui.data_models.ToDoTaskUIState

class ToDoListAdapter: RecyclerView.Adapter<ToDoListAdapter.ToDoListItemVH>() {

    private val items: ArrayList<ToDoTaskUIState> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListItemVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ToDoListItemVH(
            TodoTaskListItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ToDoListItemVH, position: Int) {
        holder.bind(items[position])
    }

    fun updateList(items: List<ToDoTaskUIState>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ToDoListItemVH(private val todoTaskListItemBinding: TodoTaskListItemBinding):
        RecyclerView.ViewHolder(todoTaskListItemBinding.root) {

        fun bind(toDoTaskUIState: ToDoTaskUIState){
            todoTaskListItemBinding.toDoTask = toDoTaskUIState
        }

    }

}