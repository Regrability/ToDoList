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
import com.example.todolist.entity.TaskLevel
import com.example.todolist.entity.TaskInfo
import com.example.todolist.ui.theme.FonColor
import com.example.todolist.ui.theme.SecondColor
import com.example.todolist.ui.theme.TextColor
import java.time.format.TextStyle

@Composable
fun TaskLevelSelector(selectedLevel: TaskLevel, onLevelSelected: (TaskLevel) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
        ) {
            Text("Task Level: ${selectedLevel.name}", color = Color.White, fontSize = 18.sp)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            TaskLevel.values().forEach { level ->
                DropdownMenuItem(
                    text = { Text(level.name) },
                    onClick = {
                        onLevelSelected(level)
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
    var selectedLevel by remember { mutableStateOf(TaskLevel.DAILY) } // Состояние для уровня задачи

    Surface(modifier = Modifier.fillMaxSize(), color = FonColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.womenaim),
                contentDescription = "womenAim",
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = "Create your task",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = TextColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Кнопка выбора уровня задачи
            TaskLevelSelector(selectedLevel) { selectedLevel = it }

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
                    /*
                    val task = TaskInfo(
                        id = 0, // Генерируем случайный ID (в реальности лучше использовать базу данных)
                        lvl = selectedLevel,
                        title = title,
                        info = info,
                        completed = false
                    )

                     */

                    // Здесь можно передавать task в базу данных или список задач
                    //println("Создана задача: $task")

                   // navManager.navigateToMainScreen()
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