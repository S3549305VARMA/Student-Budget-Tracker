package com.example.studentbudgettracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentbudgettracker.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
<<<<<<< HEAD

        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
=======
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

>>>>>>> d155b624ad3bfdeed38b2eb5673651b7353d91ce
        composable(
            "edit_expense/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
<<<<<<< HEAD
            EditExpenseScreen(
                navController,
                backStackEntry.arguments?.getInt("expenseId") ?: 0
            )
=======

>>>>>>> d155b624ad3bfdeed38b2eb5673651b7353d91ce
        }
    }
}