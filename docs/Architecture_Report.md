**Исследование архитектурного решения**

### Часть 1. Проектирование архитектуры ("To Be")
Приложение представляет собой **менеджер задач** с использованием **Jetpack Compose**. Оно включает:
- **Тип приложения**: мобильное Android-приложение.
- **Стратегия развёртывания**: Play Market / локальное APK.
- **Выбранные технологии**:
  - Kotlin + Jetpack Compose (UI) – современный декларативный подход к созданию UI.
  - Navigation Compose (управление экранами) – позволяет удобно управлять переходами между экранами.
  - State Management через `remember` и `mutableStateOf` – упрощает управление состоянием.
  - **Spring Boot (серверная часть)** – обработка запросов и управление базой данных.
  - **REST API (GET/POST запросы)** – взаимодействие клиента и сервера.
  - SharedPreferences / DataStore (для локального кэширования данных, без базы данных).
  - Hilt (рекомендуется) – для управления зависимостями.
- **Показатели качества**:
  - Производительность: быстрые UI-рендеры через Compose.
  - Масштабируемость: возможность интеграции с серверной базой данных.
  - Надёжность: минимальные зависимые библиотеки, предсказуемость состояния.
- **Реализация сквозной функциональности**:
  - Авторизация и навигация между экранами.
  - Создание, редактирование и удаление задач через сервер.
  - Фильтрация и сортировка задач.

#### **Диаграмма "To Be" (Желаемая архитектура)**
```plaintext
+--------------------+
|     UI Layer      |
|  (Jetpack Compose)|
+--------------------+
         |
         V
+--------------------+
|   ViewModel Layer |
|  (Business Logic) |
+--------------------+
         |
         V
+--------------------+
|  Data Layer       |
| (Retrofit + DataStore) |
+--------------------+
         |
         V
+--------------------+
|  REST API (GET/POST) |
+--------------------+
         |
         V
+--------------------+
|  Spring Boot Server |
|  + Database (PostgreSQL) |
+--------------------+
```

### Часть 2. Анализ архитектуры ("As Is")
Анализируя предоставленный код, приложение содержит следующие ключевые модули:
1. **Экран приветствия (`HelloScreen`)**
2. **Экран входа (`LoginScreen`) и регистрации (`RegisterScreen`)**
3. **Основной экран (`MainScreen`) с отображением списка задач**
4. **Экран создания и редактирования задачи (`DoTaskScreen`, `TaskScreen`)**
5. **Менеджер навигации (`NavManager`)**
6. **Модели данных (`TaskInfo`, `TaskLevel`)**
7. **Тема оформления (`theme`)**

Архитектура представлена в виде монолитного приложения с разделением логики по экранам. Однако, наблюдается недостаток четкой слоистой структуры (например, бизнес-логика и UI в одном месте), а также отсутствует интеграция с сервером.

#### **Диаграмма "As Is" (Текущая архитектура)**
```plaintext
+---------------------+
| UI (Jetpack Compose)|
+---------------------+
         |
         V
+---------------------+
|  Navigation Layer  |
+---------------------+
         |
         V
+---------------------+
| Бизнес-логика внутри UI |
+---------------------+
```

#### **Диаграмма классов**
```plaintext
+----------------------+
|      TaskInfo       |
|----------------------|
| - id: Int           |
| - lvl: TaskLevel    |
| - title: String     |
| - info: String      |
| - completed: Boolean|
+----------------------+
         |
         V
+----------------------+
|      TaskLevel      |
|----------------------|
| DAILY, WEEKLY,      |
| MONTHLY, YEARLY     |
+----------------------+

+----------------------+
|     NavManager      |
|----------------------|
| - navController     |
| + navigateToLogin() |
| + navigateToMain()  |
| + goBack()          |
+----------------------+

+----------------------+
|     Screens         |
|----------------------|
| HelloScreen         |
| LoginScreen         |
| RegisterScreen      |
| MainScreen          |
| DoTaskScreen        |
| TaskScreen          |
+----------------------+
```

#### **Диаграмма последовательности (создание задачи через сервер)**
```plaintext
User -> UI: Вводит данные задачи
UI -> ViewModel: Передаёт данные
ViewModel -> API (Retrofit): Отправляет POST-запрос на сервер
API -> Spring Boot Server: Обрабатывает запрос и сохраняет в БД
Server -> API: Возвращает подтверждение
API -> ViewModel: Обновляет список задач
ViewModel -> UI: Обновляет отображение
```

#### **Диаграмма вариантов использования (Use Case Diagram)**
```plaintext
+----------------------+
|      Пользователь   |
+----------------------+
         |
         V
+----------------------+
|  Создание задачи    |
|  Редактирование     |
|  Удаление задачи    |
|  Авторизация        |
|  Запрос списка задач|
+----------------------+
```

### Часть 3. Сравнение и рефакторинг
#### Отличия между "As Is" и "To Be":
- Добавление клиент-серверного взаимодействия (REST API + Spring Boot).
- Убрана локальная база данных, вместо неё – серверное хранилище с локальным кэшированием через SharedPreferences / DataStore.
- Навигация строится напрямую через `NavManager`, можно улучшить с помощью `Hilt`.
- Бизнес-логика вынесена в ViewModel, взаимодействие с данными идёт через API.
- Улучшенное управление состоянием (использование Flow/LiveData).

#### Пути улучшения:
1. Внедрение **MVVM** (Model-View-ViewModel) для разделения логики и UI.
2. Использование **Retrofit** для взаимодействия с сервером.
3. Внедрение **Hilt** для управления зависимостями.
4. Оптимизация работы с сетью через **Coroutines + Flow**.
5. Улучшение кэширования с помощью **SharedPreferences / DataStore**.

### Вывод
Обновлённая архитектура позволит масштабировать приложение, обеспечит хранение данных на сервере и упростит взаимодействие между клиентом и сервером.

