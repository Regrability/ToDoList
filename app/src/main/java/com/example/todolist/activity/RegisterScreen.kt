package com.example.todolist.activity

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.NavManager
import com.example.todolist.ui.theme.FonColor
import com.example.todolist.ui.theme.MainColor
import com.example.todolist.ui.theme.SecondColor
import com.example.todolist.ui.theme.TextColor
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(navManager: NavManager) {
    val viewModel: MyViewModel = viewModel()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val context = LocalContext.current // Получаем контекст внутри Composable
    var errorMessage by remember { mutableStateOf<String?>(null) } // Состояние для ошибки
    val coroutineScope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize(), color = FonColor) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Фоновые круги
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = MainColor.copy(alpha = 0.5f),
                    radius = 350f,
                    center = Offset(250f, 0f)
                )
                drawCircle(
                    color = MainColor.copy(alpha = 0.5f),
                    radius = 350f,
                    center = Offset(0f, 250f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(220.dp)) // Отступ после кругов

                Text(
                    text = "Welcome to Onboard! ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = TextColor
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Let’s help to meet up your tasks.",
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = TextColor,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                // Поля для ввода
                InputField("Name", value = name, onValueChange = { name = it })
                Spacer(modifier = Modifier.height(10.dp))
                InputField("Email", value = email, onValueChange = { email = it })
                Spacer(modifier = Modifier.height(10.dp))
                InputField("Password", value = password, onValueChange = { password = it }, isPassword = true)
                Spacer(modifier = Modifier.height(10.dp))
                InputField("Repeat Password", value = repeatPassword, onValueChange = { repeatPassword = it }, isPassword = true)
            }

            // Кнопка, закрепленная внизу экрана
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp), // Отступ такой же, как в HelloScreen
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        val validationError = validateInput(name, email, password, repeatPassword)
                        if (validationError != null) {
                            errorMessage = validationError
                            Toast.makeText(context, validationError, Toast.LENGTH_SHORT).show()
                        } else {
                        coroutineScope.launch {
                        val isOk = viewModel.registerUser(name, password, email)
                        if (isOk == true) {
                            navManager.navigateToLogin()
                            Toast.makeText(context, "success register", Toast.LENGTH_SHORT).show()
                        } else {
                            errorMessage = "Error registration"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                         }
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
                ) {
                    Text(text = "Register", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Текст Already have an account? Sign In
                Row {
                    Text(
                        text = "Already have an account? ",
                        fontSize = 16.sp,
                        color = TextColor
                    )
                    Text(
                        text = "Sign In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondColor,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clickable { navManager.navigateToLogin() }
                    )
                }
            }
        }
    }
}

fun validateInput(name: String, email: String, password: String, repeatPassword: String): String? {
    if (name.length < 3 || name.length > 20) {
        return "Name must be between 3 and 20 characters"
    }

    if (!email.contains("@")) {
        return "Email must contain @"
    }

    if (password.length < 6 || password.length > 20) {
        return "Password must be between 6 and 20 characters"
    }

    if (password != repeatPassword) {
        return "Passwords do not match"
    }

    return null // Валидация пройдена
}