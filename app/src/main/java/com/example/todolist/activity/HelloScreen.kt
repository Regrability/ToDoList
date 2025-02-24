package com.example.todolist.activity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.todolist.NavManager
import com.example.todolist.ui.theme.FonColor
import com.example.todolist.ui.theme.MainColor
import com.example.todolist.ui.theme.SecondColor
import com.example.todolist.ui.theme.TextColor
import com.example.todolist.R



@Composable
fun HelloScreen(navManager: NavManager) {
    Surface(modifier = Modifier.fillMaxSize(), color = FonColor) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Используем Canvas для рисования двух перекрывающихся кругов с прозрачностью
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Рисуем первый круг (левый)
                drawCircle(
                    color = MainColor.copy(alpha = 0.5f),
                    radius = 350f, // Радиус круга
                    center = Offset(250f, 0f) // Центр второго круга в пикселях (X = 600, Y = 350)
                )

                // Рисуем второй круг (правый), который будет перекрывать первый
                drawCircle(
                    color = MainColor.copy(alpha = 0.5f), // Цвет с 50% прозрачностью
                    radius = 350f, // Радиус второго круга
                    center = Offset(0f, 250f) // Центр второго круга в пикселях (X = 600, Y = 350)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),  // Паддинг для отступов
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Вставляем изображение перед текстом
                Image(
                    painter = painterResource(id = R.drawable.men), // Замените на ID изображения
                    contentDescription = "Men",
                    modifier = Modifier
                        .size(320.dp) // Устанавливаем размер изображения
                        .clip(RoundedCornerShape(12.dp)) // Округляем углы
                )
                //Spacer(modifier = Modifier.height(200.dp))
                Text(
                    text = "Gets things with TODs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = TextColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Lorem ipsum dolor sit amet consectetur. Eget sit nec et euismod. Consequat urna quam felis interdum quisque. Malesuada adipiscing tristique ut eget sed.",
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = TextColor,
                    modifier = Modifier.padding(25.dp) // Добавляем отступы вокруг текста
                )


            }

            // Кнопка, закрепленная снизу
            Button(
                onClick = { navManager.navigateToRegister() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 100.dp) // Используем корректный параметр для отступа снизу
                    .align(Alignment.BottomCenter), // Кнопка будет выровнена по центру внизу
                shape = RoundedCornerShape(8.dp),  // Добавляем немного округления углов
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondColor // Цвет фона кнопки
                )
            ) {
                Text(
                    text = "Get Started", // Текст кнопки
                    color = Color.White, // Цвет текста
                    fontSize = 18.sp
                )
            }
        }
    }
}