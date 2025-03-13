package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskModelRepository taskRepository;

  @Autowired
  private UserModelRepository userModelRepository;

  @PostMapping("/add")
  public ResponseEntity<String> addTask(@RequestBody TaskDTO taskDTO) {
    try {
      TaskModel task = new TaskModel();
      task.setTitle(taskDTO.getTitle());
      task.setInfo(taskDTO.getInfo());
      task.setTaskLvl(taskDTO.getTaskLvl());
      task.setDone(taskDTO.isDone());

      UserModel user = userModelRepository.findById(taskDTO.getUserId())
          .orElseThrow(() -> new RuntimeException("User not found"));
      task.setUser(user);

      taskRepository.save(task);
      return ResponseEntity.ok("Task added successfully");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error adding task: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteTask(@PathVariable Long id) {
    try {
      if (!taskRepository.existsById(id)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Task not found");
      }

      taskRepository.deleteById(id);
      return ResponseEntity.ok("Task deleted successfully");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error deleting task: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
    try {
      TaskModel task = taskRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Task not found"));

      if (taskDTO.getTitle() != null) {
        task.setTitle(taskDTO.getTitle());
      }
      if (taskDTO.getInfo() != null) {
        task.setInfo(taskDTO.getInfo());
      }
      if (taskDTO.getTaskLvl() != null) {
        task.setTaskLvl(taskDTO.getTaskLvl());
      }
      if (taskDTO.isDone()) {
        task.setDone(taskDTO.isDone());
      }

      taskRepository.save(task);
      return ResponseEntity.ok("Task updated successfully");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error updating task: " + e.getMessage());
    }
  }

  // Метод для получения всех задач для определенного user_id
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<TaskModel>> getTasksByUserId(@PathVariable Long userId) {
    List<TaskModel> tasks = taskRepository.findByUserId(userId); // Используем userId напрямую
    if (tasks.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Возвращаем 404, если задачи не найдены
    }

    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/getAll")
  public Iterable<TaskModel> getAllTasks() {
    return taskRepository.findAll();
  }
}