package com.example.demo;

import java.util.List;

public class UserResponse {
 private Long id; // Уникальный идентификатор пользователя
 private String email; // Электронная почта пользователя
 private String name; // Имя пользователя
 private List<TaskModel> tasks; // Список задач пользователя

 // Конструктор
 public UserResponse(Long id, String email, String name, List<TaskModel> tasks) {
  this.id = id;
  this.email = email;
  this.name = name;
  this.tasks = tasks;
 }

 // Геттеры
 public Long getId() {
  return id;
 }

 public String getEmail() {
  return email;
 }

 public String getName() {
  return name;
 }

 public List<TaskModel> getTasks() {
  return tasks;
 }
}