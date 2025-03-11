package com.example.todolist.activity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Модель для запроса входа
data class LoginRequest(
    val email: String,
    val password: String
)

// Модель для ответа от сервера (теперь только ID пользователя)
data class LoginResponse(val id_user: Int)


interface ApiService {
    @POST("users/loginId")
    fun loginUser(@Body request: LoginRequest): Call<Int>  // Ожидаем целое число как ответ
}


// Объект для Retrofit
object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.91.55:8080/") // Укажите свой адрес сервера
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

class MyViewModel : ViewModel() {
    var loginResponse by mutableStateOf<String?>(null)
        private set

    var userId by mutableStateOf<Int?>(null) // Храним ID пользователя
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Обработчик логина
    fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)

        RetrofitInstance.api.loginUser(request).enqueue(object : Callback<Int> {  // Ожидаем возвращаемое значение типа Int
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val userId = response.body()  // Получаем ID пользователя
                    loginResponse = "Успех: ID пользователя = $userId"
                    userId?.let { this@MyViewModel.userId = it } // Сохраняем ID пользователя

                    // Логирование
                    Log.d("LOGIN", "Ответ от сервера: $userId")
                    Log.d("LOGIN", "ID пользователя: $userId")
                } else {
                    errorMessage = "Ошибка: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                errorMessage = "Ошибка сети: ${t.message}"
                Log.e("LOGIN", "Ошибка: ${t.message}")
            }
        })
    }

}
