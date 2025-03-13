package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  // private static final Logger logger =
  // LoggerFactory.getLogger(DataBaseController.class);

  @Autowired
  private UserModelRepository userModelRepository;

  @Autowired
  private TaskModelRepository taskRepository;

  @PostMapping("add")
  public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
    // Check if the username already exists
    if (userModelRepository.existsByName(userDTO.getName())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken");
    }

    UserModel user = new UserModel();
    user.setName(userDTO.getName());
    user.setPassword(userDTO.getPassword());
    user.setEmail(userDTO.getEmail());
    user.setPathToImage(userDTO.getPathToImage());

    userModelRepository.save(user);

    return ResponseEntity.ok("User added successfully");
  }

  // Метод для обновления пути к изображению пользователя
  @PutMapping("/user/{id}/image")
  public ResponseEntity<?> updateUserImage(@PathVariable Long id, @RequestBody String pathToImage) {
    UserModel user = userModelRepository.findById(id).orElse(null);

    if (user != null) {
      // Обновляем путь к изображению
      user.setPathToImage(pathToImage); // Предполагая, что есть метод setImagePath
      userModelRepository.save(user); // Сохраняем обновленного пользователя
      return ResponseEntity.ok("Image path updated successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
  }

  // Метод для проверки email и password
  @PostMapping("/onlyLogin")
  public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
    UserModel user = userModelRepository.findByEmail(email);

    if (user != null && user.getPassword().equals(password)) {
      return ResponseEntity.ok("Login successful");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
  }

  // Метод для логина и получения данных о пользователе
  @PostMapping("/login")
  public ResponseEntity<?> loginWResp(@RequestBody LoginRequest loginRequest) {
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    UserModel user = userModelRepository.findByEmail(email);

    if (user != null && user.getPassword().equals(password)) {
      List<TaskModel> tasks = taskRepository.findByUserId(user.getId());

      // Создаем объект ответа
      UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), user.getName(), tasks);
      return ResponseEntity.ok(userResponse); // Возвращаем информацию о пользователе и его задачи
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
  }

  // Метод для логина и получения ID пользователя
  @PostMapping("/loginId")
  public ResponseEntity<?> loginWRespId(@RequestBody LoginRequest loginRequest) {
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    UserModel user = userModelRepository.findByEmail(email);

    if (user != null && user.getPassword().equals(password)) {
      // Возвращаем только ID пользователя
      return ResponseEntity.ok(user.getId());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
  }

  // Метод для получения пользователя по ID
  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    UserModel user = userModelRepository.findById(id).orElse(null);

    if (user != null) {
      // Возвращаем информацию о пользователе
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
  }

  @GetMapping("getAll")
  public String getAllInfo() {
    StringBuilder response = new StringBuilder();
    Iterable<UserModel> userModel = userModelRepository.findAll();

    userModel.forEach(user -> {
      response.append("User ID: ").append(user.getId())
          .append(", Name: ").append(user.getName())
          .append(", Password: ").append(user.getPassword())
          .append("\n");
    });

    return response.toString();
  }
}
