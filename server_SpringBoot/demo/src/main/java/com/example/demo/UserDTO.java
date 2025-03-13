package com.example.demo;

public class UserDTO {
 private String name;
 private String password;
 private String email;
 private String pathToImage;

 // Getters and Setters
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

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getPathToImage() {
  return pathToImage;
 }

 public void setPathToImage(String pathToImage) {
  this.pathToImage = pathToImage;
 }
}