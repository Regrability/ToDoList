package com.example.todolist.activity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.entity.TaskInfo
import com.example.todolist.entity.TaskLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Модель для запроса входа
data class LoginRequest(
    val email: String,
    val password: String
)

// Модель для запроса регистарции
data class RegisterRequest(
    val name: String,
    val password: String,
    val email: String,

)

data class TaskRequest(
    val title: String,
    val info: String,
    val taskLvl: String,
    val done: Boolean,
    val user: Int
)

data class TaskResponse(
    val id: Int,
    val title: String,
    val info: String,
    val taskLvl: String,
    val done: Boolean,
    val user: Int
)

fun TaskResponse.toTaskInfo(): TaskInfo {
    return TaskInfo(
        id = this.id,
        lvl = TaskLevel.valueOf(this.taskLvl.uppercase()),
        title = this.title,
        info = this.info,
        completed = this.done,
        user_id = this.user
    )
}

fun TaskInfo.toTaskRequest(): TaskRequest {
    return TaskRequest(
        title = this.title,
        info = this.info,
        taskLvl = this.lvl.name.lowercase(),
        done = this.completed,
        user = this.user_id
    )
}


// Интерфейс API
interface ApiService {
    @POST("users/loginId")
    suspend fun loginUser(@Body request: LoginRequest): Response<Int>

    @POST("users/add")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Void>

    @POST("tasks/add")
    suspend fun addTask(@Body request: TaskRequest): Response<Void>

    @GET("tasks/user/{userId}")
    suspend fun getTasksByUserId(@Path("userId") userId: Int): Response<List<TaskResponse>>

}

// Объект для Retrofit
object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.91.55:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

class MyViewModel : ViewModel() {
    var userId by mutableStateOf<Int?>(null)
        private set

    private val _tasks = MutableStateFlow<List<TaskInfo>>(emptyList())
    val tasks: StateFlow<List<TaskInfo>> = _tasks

    var errorMessage by mutableStateOf<String?>(null)
        private set

    //метод для добавления задачи
    suspend fun addTask(task : TaskInfo): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = task.toTaskRequest()
                val response = RetrofitInstance.api.addTask(request)

                if (response.isSuccessful) {
                    Log.d("TASK111", "добавлено успешно")
                    true
                } else {
                    Log.e("TASK111", "Ошибка добавления задачи: ${response.code()}")
                    false
                }
            } catch (e: Exception) {
                Log.e("TASK111", "Ошибка сети: ${e.message}")
                false
            }
        }
    }

    //метод регистрации
    suspend fun registerUser(name: String, password: String, email: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = RegisterRequest(name, password, email)
                val response = RetrofitInstance.api.registerUser(request)

                if (response.isSuccessful) {
                    Log.d("REGISTER", "Регистрация успешна")
                    true
                } else {
                    Log.e("REGISTER", "Ошибка регистрации: ${response.code()}")
                    false
                }
            } catch (e: Exception) {
                Log.e("REGISTER", "Ошибка сети: ${e.message}")
                false
            }
        }
    }

    // Функция для логина
    suspend fun loginUser(email: String, password: String): Int? {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitInstance.api.loginUser(request)

                if (response.isSuccessful) {
                    response.body()?.also { id ->
                        userId = id
                        Log.d("LOGIN", "Успешный вход, ID: $id")
                    }
                } else {
                    Log.e("LOGIN", "Ошибка сервера: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("LOGIN", "Ошибка сети: ${e.message}")
                null
            }
        }
    }

    fun getTasks(userId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTasksByUserId(userId)
                Log.d("TASKS111", "Запрашиваю задачи для userId: $userId")
                if (response.isSuccessful) {
                    response.body()?.let { taskList ->
                        val taskInfoList = taskList.map { it.toTaskInfo() }
                        _tasks.update { taskInfoList }


                        Log.d("TASKS111", "Задачи загружены: $taskInfoList")
                    }
                } else {
                    errorMessage = "Ошибка загрузки задач: ${response.code()}"
                    Log.e("TASKS111", "Ошибка загрузки задач: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage = "Ошибка сети: ${e.message}"
                Log.e("TASKS111", "Ошибка сети: ${e.message}")
            }
        }
    }

}