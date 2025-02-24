package com.example.todolist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.activity.DoTaskScreen
import com.example.todolist.activity.HelloScreen
import com.example.todolist.activity.LoginScreen
import com.example.todolist.activity.MainScreen
import com.example.todolist.activity.RegisterScreen
import com.example.todolist.ui.theme.ToDoListTheme

class NavManager(private val navController: NavController) {
    fun navigateToLogin() {
        navController.navigate("login_screen")
    }

    fun navigateToRegister() {
        navController.navigate("register_screen")
    }

    fun navigateToMainScreen() {
        navController.navigate("main_screen")
    }

    fun navigateToDoTaskScreen() {
        navController.navigate("do_task_screen")
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
    val navManager = remember { NavManager(navController) } // Создаём экземпляр NavManager

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
            MainScreen(navManager)
        }
        composable("do_task_screen") {
            DoTaskScreen(navManager)
        }
    }

}
