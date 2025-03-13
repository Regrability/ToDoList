package com.example.demo;

public class TaskDTO {
 private String title;
 private String info;
 private String taskLvl;
 private boolean done;
 private Long userId;

 // Getters and Setters
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

 public Long getUserId() {
  return userId;
 }

 public void setUserId(Long userId) {
  this.userId = userId;
 }
}