package com.vasillyubenov.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (private val taskList: MutableList<Task>,
                   private val saveTasks: () -> Unit,
                   private  val deleteTask: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.taskText)
        val taskCheckBox: CheckBox = view.findViewById(R.id.taskCheckBox)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)

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

        holder.deleteButton.setOnClickListener {
            deleteTask(task)
        }
    }

    override fun getItemCount(): Int = taskList.size
}