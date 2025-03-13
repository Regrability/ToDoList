# ToDoList

ToDoList - это мобильное приложение для управления задачами, разработанное с использованием Jetpack Compose и Kotlin.

## 📌 Основные функции
- Создание, редактирование и удаление задач
- Фильтрация задач по статусу (выполненные, в процессе)
- Поддержка категорий задач: Daily, Weekly, Monthly, Yearly
- Авторизация и регистрация пользователей
- Хранение данных в базе данных

## 🛠️ Технологии
- **Язык**: Kotlin
- **Фреймворк**: Jetpack Compose
- **Архитектура**: MVVM
- **API**: Retrofit + Spring Boot (Backend)

## 📂 Структура проекта
```plaintext
app/
├── src/main/kotlin/com/example/todolist/
│   ├── activity/  # UI-компоненты приложения
│   ├── entity/    # Модели данных
│   ├── ui/theme/  # Темы и стили
│   ├── NavManager.kt  # Управление навигацией
│   ├── MyViewModel.kt # ViewModel приложения
│   ├── MainActivity.kt # Точка входа в приложение
└── backend/
    ├── src/main/java/com/example/demo/
    │   ├── controller/  # Контроллеры Spring Boot
    │   ├── entity/      # Сущности базы данных
    │   ├── repository/  # Репозитории Spring Data JPA
    │   ├── service/     # Логика приложения
