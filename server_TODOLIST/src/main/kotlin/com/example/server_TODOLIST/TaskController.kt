package com.example.server_TODOLIST

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/** Определяем класс Task */
data class Task(val title: String, val description: String)

@CrossOrigin(origins = ["*"]) // Разрешаем запросы с любых доменов
@RestController
@RequestMapping("/tasks")
class TaskController {
    private val tasks = mutableListOf<Task>()

    @GetMapping(produces = ["application/json; charset=UTF-8"])
    fun getTasks(): List<Task> = tasks

    @PostMapping(consumes = ["application/json"], produces = ["application/json; charset=UTF-8"])
    fun addTask(@RequestBody task: Task): ResponseEntity<String> {
        if (task.title.isBlank() || task.description.isBlank()) {
            return ResponseEntity.badRequest().body("Ошибка: заголовок и описание не могут быть пустыми!")
        }
        tasks.add(task)
        return ResponseEntity.ok("Задача добавлена: ${task.title} - ${task.description}")
    }
}