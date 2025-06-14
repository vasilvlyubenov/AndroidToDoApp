package com.vasillyubenov.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (private val taskList: MutableList<Task>,
                   private val saveTasks: () -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.taskText)
        val taskCheckBox: CheckBox = view.findViewById(R.id.taskCheckBox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTitle.text = task.title
        holder.taskCheckBox.isChecked = task.isDone

        holder.taskTitle.paint.isStrikeThruText = task.isDone

        holder.taskCheckBox.setOnCheckedChangeListener(null)
        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            holder.taskTitle.paint.isStrikeThruText = isChecked
            saveTasks()
        }

        holder.itemView.setOnClickListener {
            taskList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = taskList.size
}