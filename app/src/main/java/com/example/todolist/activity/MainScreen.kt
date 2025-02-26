package com.example.todolist.activity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.NavManager
import com.example.todolist.entity.TaskInfo
import com.example.todolist.entity.TaskLevel
import com.example.todolist.ui.theme.DAILYColor
import com.example.todolist.ui.theme.FonColor
import com.example.todolist.ui.theme.MONTHLYColor
import com.example.todolist.ui.theme.SecondColor
import com.example.todolist.ui.theme.WEEKLYColor
import com.example.todolist.ui.theme.YEARLYColor


@Composable
fun SortingButton(onSortSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val sortingOptions = listOf(
        "slow->fast \"completed\"",
        "fast->slow \"completed\"",
        "slow->fast \"in progress\"",
        "fast->slow \"in progress\""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(horizontal = 7.dp)
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 17.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
        ) {
            Text("Sort", color = Color.White, fontSize = 18.sp)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            sortingOptions.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSortSelected(index) // Передаем индекс сортировки в MainScreen
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageCard(
    message: TaskInfo,
    onNavigateToTask: () -> Unit,
    onToggleTaskCompletion: (TaskInfo) -> Unit,
    onDeleteTask: (TaskInfo) -> Unit
) {
    var borderColor = DAILYColor
    if (message.lvl == TaskLevel.WEEKLY) borderColor = WEEKLYColor
    if (message.lvl == TaskLevel.MONTHLY) borderColor = MONTHLYColor
    if (message.lvl == TaskLevel.YEARLY) borderColor = YEARLYColor

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                Button(onClick = {
                    onDeleteTask(message)
                    showDeleteDialog = false
                },
                    colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false },colors = ButtonDefaults.buttonColors(containerColor = SecondColor)) {
                    Text(" Back ")
                }
            },
            title = { Text("Delete task?") },
            text = { Text("Are you sure, you want to delete this task?") }
        )
    }

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .border(2.dp, borderColor, MaterialTheme.shapes.medium)
            .combinedClickable(
                onClick = { onNavigateToTask() },
                onDoubleClick = { onToggleTaskCompletion(message) },
                onLongClick = { showDeleteDialog = true }
            ),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = message.title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Уровень: ${message.lvl}",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "\n${message.info}",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Checkbox(
                checked = message.completed,
                onCheckedChange = { onToggleTaskCompletion(message) },
                modifier = Modifier
                    .size(64.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}






@Composable
fun MainScreen(navManager: NavManager) {
    var sortId by remember { mutableStateOf(0) }
    var tasks by remember { mutableStateOf(getInitialTasks()) }

    LaunchedEffect(sortId) {
        tasks = sortTasks(tasks, sortId)
    }

    fun toggleTaskCompletion(task: TaskInfo) {
        tasks = tasks.map {
            if (it.id == task.id) it.copy(completed = !it.completed) else it
        }
    }

    fun deleteTask(task: TaskInfo) {
        tasks = tasks.filter { it.id != task.id }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = FonColor) {
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val screenWidth = size.width
                val screenHeight = size.height

                drawRect(
                    color = SecondColor,
                    size = Size(screenWidth, screenHeight * 0.30f),
                    topLeft = Offset(0f, 0f)
                )

                drawCircle(
                    color = FonColor.copy(alpha = 0.5f),
                    radius = 350f,
                    center = Offset(250f, 0f)
                )

                drawCircle(
                    color = FonColor.copy(alpha = 0.5f),
                    radius = 350f,
                    center = Offset(0f, 250f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 280.dp)
            ) {
                Row {
                    SortingButton(onSortSelected = { newSortId ->
                        sortId = newSortId
                    })
                    Button(
                        onClick = { navManager.navigateToDoTaskScreen() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SecondColor)
                    ) {
                        Text(text = "Add task", color = Color.White, fontSize = 18.sp)
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tasks) { task ->
                        MessageCard(
                            message = task,
                            onNavigateToTask = {navManager.navigateToTaskScreen()},
                            onToggleTaskCompletion = ::toggleTaskCompletion,
                            onDeleteTask = ::deleteTask
                        )
                    }
                }
            }
        }
    }
}

fun sortTasks(tasks: List<TaskInfo>, type: Int): List<TaskInfo> {
    return when (type) {
        0 -> tasks.sortedWith(compareByDescending<TaskInfo> { it.completed }.thenBy { it.lvl.ordinal })
        1 -> tasks.sortedWith(compareByDescending<TaskInfo> { it.completed }.thenByDescending { it.lvl.ordinal })
        2 -> tasks.sortedWith(compareBy<TaskInfo> { it.completed }.thenBy { it.lvl.ordinal })
        3 -> tasks.sortedWith(compareBy<TaskInfo> { it.completed }.thenByDescending { it.lvl.ordinal })
        else -> tasks // если тип не найден, возвращаем исходный список
    }
}

fun getInitialTasks() = listOf(
    TaskInfo(1, TaskLevel.DAILY, "Утренняя зарядка", "Сделать 10-минутную зарядку", true),
    TaskInfo(2, TaskLevel.WEEKLY, "Чтение книги", "Прочитать 50 страниц книги", false),
    TaskInfo(3, TaskLevel.MONTHLY, "Отчет по проекту", "Подготовить и сдать отчет по проекту", true),
    TaskInfo(4, TaskLevel.YEARLY, "Медосмотр", "Пройти ежегодный медицинский осмотр", false),
    TaskInfo(5, TaskLevel.DAILY, "Прогулка", "Пройти 5 км на свежем воздухе", true),
    TaskInfo(6, TaskLevel.WEEKLY, "Спортзал", "Позаниматься в спортзале 3 раза в неделю", false),
    TaskInfo(7, TaskLevel.MONTHLY, "Курс по программированию", "Пройти 10 уроков Kotlin", true),
    TaskInfo(8, TaskLevel.YEARLY, "Отпуск", "Спланировать и провести отпуск", false),
    TaskInfo(9, TaskLevel.DAILY, "Медитация", "Медитировать 10 минут", false),
    TaskInfo(10, TaskLevel.WEEKLY, "Фильмы", "Посмотреть новый фильм", true),
    TaskInfo(11, TaskLevel.MONTHLY, "Бюджет", "Спланировать расходы на месяц", false),
    TaskInfo(12, TaskLevel.YEARLY, "Курс первой помощи", "Пройти курс оказания первой помощи", true),
    TaskInfo(13, TaskLevel.DAILY, "Витамины", "Принимать витамины", true),
    TaskInfo(14, TaskLevel.WEEKLY, "Прогулка в парке", "Провести вечер в парке", false),
    TaskInfo(15, TaskLevel.MONTHLY, "Генеральная уборка", "Провести уборку в квартире", true),
    TaskInfo(16, TaskLevel.YEARLY, "Обновить резюме", "Обновить свое резюме", false),
    TaskInfo(17, TaskLevel.DAILY, "Пить воду", "Выпивать 2 литра воды", false),
    TaskInfo(18, TaskLevel.WEEKLY, "Творчество", "Создать что-то новое", true),
    TaskInfo(19, TaskLevel.MONTHLY, "Здоровье", "Пройти обследование у врача", false),
    TaskInfo(20, TaskLevel.YEARLY, "Учеба", "Пройти новый курс", true)
)