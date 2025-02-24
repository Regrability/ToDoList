package com.example.todolist.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.NavManager
import com.example.todolist.R
import com.example.todolist.ui.theme.FonColor
import com.example.todolist.ui.theme.SecondColor
import com.example.todolist.ui.theme.TextColor
import java.time.format.TextStyle

@Composable
fun RareButton() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("DAILY") } // Начальное значение

    val sortingOptions = listOf("DAILY", "WEEKLY", "MONTHLY", "YEARLY")

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center // Центрирует содержимое
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
        ) {
            Text("Task Level: $selectedOption", color = Color.White, fontSize = 18.sp)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            sortingOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option // Обновляем выбранный вариант
                        expanded = false
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoTaskScreen(navManager: NavManager) {

    var title by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }


    Surface(modifier = Modifier.fillMaxSize(), color = FonColor) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp)) // Отступ после кругов

                Image(
                    painter = painterResource(id = R.drawable.womenaim), // Замените на ID изображения
                    contentDescription = "womenAim",
                    modifier = Modifier
                        .size(240.dp) // Устанавливаем размер изображения
                        .clip(RoundedCornerShape(12.dp)) // Округляем углы
                )

                Text(
                    text = "Create your task ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = TextColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                RareButton()

                // Поля для ввода

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp), // Уменьшаем отступ перед линией

                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ), // Увеличенный шрифт текста
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent, // Убираем стандартную границу
                        focusedBorderColor = Color.Transparent,
                        cursorColor = SecondColor,
                        focusedLabelColor = TextColor,
                    ),
                    shape = RectangleShape, // Убираем закругления
                    placeholder = { Text("Title", fontSize = 20.sp, color = TextColor) } // Увеличенный плейсхолдер
                )

                Divider(
                    color = TextColor,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))


                OutlinedTextField(
                    value = info,
                    onValueChange = { info = it },
                    label = { Text("Info") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp), // Устанавливаем высоту
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = SecondColor,
                        cursorColor = SecondColor,
                        focusedLabelColor = TextColor,
                    ),
                    maxLines = 10, // Позволяем многострочный ввод
                    singleLine = false, // Разрешаем перенос строк
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences, // Автозаглавные буквы
                        imeAction = ImeAction.Default // Стандартное поведение Enter
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))



            }

            // Кнопка, закрепленная внизу экрана
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {

                        navManager.navigateToMainScreen()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
                ) {
                    Text(text = "CREATE", color = Color.White, fontSize = 18.sp)
                }
                Button(
                    onClick = {

                        navManager.goBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
                ) {
                    Text(text = "Go Back", color = Color.White, fontSize = 18.sp)
                }

            }

    }
}