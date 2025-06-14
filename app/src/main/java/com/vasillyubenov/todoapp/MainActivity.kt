package com.vasillyubenov.todoapp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadTasks()

        val recyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
        val taskInput = findViewById<EditText>(R.id.taskInput)
        val addButton = findViewById<Button>(R.id.addButton)

        taskAdapter = TaskAdapter(taskList, ::saveTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        addButton.setOnClickListener {
            val taskText = taskInput.text.toString()
            if (taskText.isNotBlank()) {
                val task = Task(taskText)
                taskList.add(task)
                taskInput.text.clear()
                taskAdapter.notifyItemInserted(taskList.size - 1)
                recyclerView.scrollToPosition(taskList.size - 1)
                saveTasks()
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveTasks() {
        val sharedPref = getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        sharedPref.edit {
            val json = Gson().toJson(taskList)
            putString("task_list", json)
        }
    }

    private fun loadTasks() {
        val sharedPref = getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        val json = sharedPref.getString("task_list", null)

        if (json != null) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            val loadedList: MutableList<Task> = Gson().fromJson(json, type)
            taskList.clear()
            taskList.addAll(loadedList)
        }
    }
}