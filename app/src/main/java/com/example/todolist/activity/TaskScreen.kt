package com.example.todolist.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.NavManager
import com.example.todolist.entity.TaskInfo
import com.example.todolist.ui.theme.FonColor
import com.example.todolist.ui.theme.SecondColor
import com.example.todolist.ui.theme.TextColor



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(navManager: NavManager, viewModel: MyViewModel, task: TaskInfo) {

    var title by remember { mutableStateOf(task.title) }
    var info by remember { mutableStateOf(task.info) }
    var selectedLevel by remember { mutableStateOf(task.lvl) } // Загружаем уровень задачи

    Surface(modifier = Modifier.fillMaxSize(), color = FonColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Task Level: $selectedLevel",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = TextColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = SecondColor,
                    focusedLabelColor = TextColor,
                ),
                shape = RectangleShape,
                placeholder = { Text("Title", fontSize = 20.sp, color = TextColor) }
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
                    .height(200.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = SecondColor,
                    cursorColor = SecondColor,
                    focusedLabelColor = TextColor,
                ),
                maxLines = 10,
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Default
                )
            )

            Spacer(modifier = Modifier.height(30.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val updatedTask = task.copy(title = title, info = info)
                    viewModel.updateTaskSafe(updatedTask) // Используем безопасный вызов
                    navManager.navigateToMainScreen()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
            ) {
                Text(text = "Change", color = Color.White, fontSize = 18.sp)
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
