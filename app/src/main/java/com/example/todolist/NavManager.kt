package com.example.todolist

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.activity.*
import com.example.todolist.entity.TaskInfo

class TaskViewModel : ViewModel() {
    var currentTask: TaskInfo? = null
        private set

    fun setCurrentTask(task: TaskInfo) {
        currentTask = task
    }
}

class NavManager(
    private val navController: NavController,
    private val userIdState: MutableState<Int?>,
    private val taskViewModel: TaskViewModel
) {

    fun navigateToLogin() {
        navController.navigate("login_screen")
    }

    fun navigateToRegister() {
        navController.navigate("register_screen")
    }

    fun navigateToMainScreen(user_id: Int) {
        userIdState.value = user_id
        navController.navigate("main_screen")
    }

    fun navigateToMainScreen() {
        if (userIdState.value != null) {
            navController.navigate("main_screen")
        }
    }

    fun navigateToDoTaskScreen() {
        navController.navigate("do_task_screen")
    }

    fun navigateToTaskScreen(task: TaskInfo) {
        taskViewModel.setCurrentTask(task)  // Передаём задачу в ViewModel
        navController.navigate("task_screen")
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun goBackTo(route: String, inclusive: Boolean = false) {
        navController.popBackStack(route, inclusive)
    }

    fun clearStackAndNavigate(route: String) {
        navController.navigate(route) {
            popUpTo(0)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val userId = rememberSaveable { mutableStateOf<Int?>(null) }
    val taskViewModel: TaskViewModel = viewModel()
    val navManager = remember { NavManager(navController, userId, taskViewModel) }
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
                MainScreen(navManager, viewModel, currentUserId)
            }
        }
        composable("do_task_screen") {
            val currentUserId = userId.value
            if (currentUserId != null) {
                DoTaskScreen(navManager, viewModel, currentUserId)
            }
        }
        composable("task_screen") {
            val task = taskViewModel.currentTask
            if (task != null) {
                TaskScreen(navManager, viewModel, task)
            }
        }
    }
}