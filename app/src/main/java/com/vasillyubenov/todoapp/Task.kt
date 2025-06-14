package com.vasillyubenov.todoapp

data class Task(
    val title: String,
    var isDone: Boolean = false
)