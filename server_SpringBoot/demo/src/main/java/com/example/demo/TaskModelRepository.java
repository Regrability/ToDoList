package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TaskModelRepository extends CrudRepository<TaskModel, Long> {
 List<TaskModel> findByUserId(Long userId);
}