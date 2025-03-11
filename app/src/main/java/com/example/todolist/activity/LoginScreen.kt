package com.example.todolist.activity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.NavManager
import com.example.todolist.ui.theme.FonColor
import com.example.todolist.ui.theme.MainColor
import com.example.todolist.ui.theme.SecondColor
import com.example.todolist.ui.theme.TextColor
import com.example.todolist.R



@Composable
fun LoginScreen(navManager: NavManager) {

    val viewModel: MyViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


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
                    text = "Welcome back ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = TextColor
                )
                // Вставляем изображение перед текстом
                Image(
                    painter = painterResource(id = R.drawable.womenchild), // Замените на ID изображения
                    contentDescription = "WomenChild",
                    modifier = Modifier
                        .size(240.dp) // Устанавливаем размер изображения
                        .clip(RoundedCornerShape(12.dp)) // Округляем углы
                )

                // Поля для ввода
                InputField("Email", value = email, onValueChange = { email = it })
                Spacer(modifier = Modifier.height(10.dp))
                InputField("Password", value = password, onValueChange = { password = it }, isPassword = true)

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Forget password ?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondColor,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            //TODO
                        }
                )
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
                        println("Email: $email, Password: $password")
                        viewModel.loginUser(email, password)
                        //navManager.navigateToMainScreen()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
                ) {
                    Text(text = "Login", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Текст Already have an account? Sign In
                Row {
                    Text(
                        text = "Don’t have an account ? ",
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
                            .clickable { navManager.navigateToRegister() }
                    )
                }
            }
        }
    }
}

// Поле для ввода с изменяемым значением
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextColor) }, // Цвет метки
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)), // Скругление углов
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = SecondColor,
            cursorColor = SecondColor,
            focusedLabelColor = SecondColor,
        ),
        shape = RoundedCornerShape(8.dp) // Скругление рамки
    )
}
