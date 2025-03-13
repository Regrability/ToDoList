package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TaskModel {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String title;
 private String info;
 private String taskLvl;
 private boolean done;

 @ManyToOne
 @JoinColumn(name = "user_id")
 @JsonIgnore // Исключаем пользователя из сериализации
 private UserModel user;

 // Геттеры и сеттеры
 public Long getId() { // Геттер для id
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getTitle() {
  return title;
 }

 public void setTitle(String title) {
  this.title = title;
 }

 public String getInfo() {
  return info;
 }

 public void setInfo(String info) {
  this.info = info;
 }

 public String getTaskLvl() {
  return taskLvl;
 }

 public void setTaskLvl(String taskLvl) {
  this.taskLvl = taskLvl;
 }

 public boolean isDone() {
  return done;
 }

 public void setDone(boolean done) {
  this.done = done;
 }

 public UserModel getUser() {
  return user;
 }

 public void setUser(UserModel user) {
  this.user = user;
 }

 public TaskModel() {
 }

 public TaskModel(Long id, String title, String info, String taskLvl, boolean done, UserModel user) {
  this.id = id;
  this.title = title;
  this.info = info;
  this.taskLvl = taskLvl;
  this.done = done;
  this.user = user;
 }
}