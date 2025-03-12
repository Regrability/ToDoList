package com.example.todolist

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.activity.*
import com.example.todolist.entity.TaskInfo

class NavManager(private val navController: NavController, private val userIdState: MutableState<Int?>) {

    fun navigateToLogin() {
        navController.navigate("login_screen")
    }

    fun navigateToRegister() {
        navController.navigate("register_screen")
    }

    // Устанавливаем userId и переходим на главный экран
    fun navigateToMainScreen(user_id: Int) {
        userIdState.value = user_id
        navController.navigate("main_screen")
    }

    // Переход на главный экран, используя сохранённый userId
    fun navigateToMainScreen() {
        if (userIdState.value != null) {
            navController.navigate("main_screen")
        } else {
            // Здесь можно обработать случай, когда userId не установлен
            //TODO
        }
    }

    fun navigateToDoTaskScreen() {
        navController.navigate("do_task_screen")
    }

    fun navigateToTaskScreen(task: TaskInfo) {
        navController.navigate("task_screen/${task.id}") // Передаем id задачи
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun goBackTo(route: String, inclusive: Boolean = false) {
        navController.popBackStack(route, inclusive)
    }

    fun clearStackAndNavigate(route: String) {
        navController.navigate(route) {
            popUpTo(0) // Удаляет весь стек
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    val userId = rememberSaveable { mutableStateOf<Int?>(null) } // Переменная для хранения user_id
    val navManager = remember { NavManager(navController, userId) } // Создаём экземпляр NavManager
    val viewModel: MyViewModel = viewModel()

    NavHost(navController = navController, startDestination = "hello_screen") {

        composable("hello_screen") {
            HelloScreen(navManager)
        }
        composable("login_screen") {
            LoginScreen(navManager)
        }
        composable("register_screen") {
            RegisterScreen(navManager)
        }
        composable("main_screen") {
            val currentUserId = userId.value
            if (currentUserId != null) {
                MainScreen(navManager, currentUserId)
            } else {
                // Ошибка, если userId не установлен
            }
        }
        composable("do_task_screen") {
            val currentUserId = userId.value
            if (currentUserId != null) {
                DoTaskScreen(navManager, viewModel, currentUserId)
            } else {
                // Ошибка, если userId не установлен
            }
        }
        composable("task_screen/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()

            if (taskId != null) {
                TaskScreen(navManager, taskId)
            } else {
                // Ошибка, если taskId не передан
            }
        }
    }
}
