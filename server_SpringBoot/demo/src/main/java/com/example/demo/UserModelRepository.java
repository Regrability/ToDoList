package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface UserModelRepository extends CrudRepository<UserModel, Long> {
 boolean existsByName(String name);

 UserModel findByEmail(String email);
}