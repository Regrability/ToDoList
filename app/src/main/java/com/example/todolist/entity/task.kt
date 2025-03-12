package com.example.todolist.entity

enum class TaskLevel {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

data class TaskInfo(

    val id: Int? = null,
    val lvl: TaskLevel,
    val title: String,
    val info: String,
    val completed : Boolean,
    val user_id : Int

)
