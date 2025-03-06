package com.example.todolist.activity

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Модель задачи
data class Task(
    val title: String,
    val description: String
)

// API-интерфейс
interface ApiService {
    @GET("tasks")
    fun getTasks(): Call<List<Task>>
}

// Объект для Retrofit
object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.239.179:8080/") // Подставьте свой IP
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}


class TaskViewModel : ViewModel() {
    var tasks = mutableStateListOf<Task>()
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchTasks() {
        RetrofitInstance.api.getTasks().enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    tasks.clear()
                    tasks.addAll(response.body() ?: emptyList())
                    Log.d("TASKS111", "Получено: $tasks")
                } else {
                    errorMessage = "Ошибка: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                errorMessage = "Ошибка загрузки: ${t.message}"
                Log.e("TASKS111", "Ошибка: ${t.message}")
            }
        })
    }
}

// UI для отображения списка задач
@Composable
fun TaskScreenGet(viewModel: TaskViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchTasks()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Список задач", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.tasks) { task ->
                TaskItem(task)
            }
        }
    }
}

// Отображение одной задачи
@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}



