package com.example.demo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserModel {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Long id;
 private String name, password, email;
 @OneToMany(mappedBy = "user") // Связь с задачами
 private List<TaskModel> tasks;
 private String pathToImage;

 public String getPathToImage() {
  return pathToImage;
 }

 public void setPathToImage(String pathToImage) {
  this.pathToImage = pathToImage;
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public UserModel(Long id, String name, String password, String pathString) {
  this.id = id;
  this.name = name;
  this.password = password;
  this.pathToImage = pathString;
 }

 public UserModel() {
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }
}
