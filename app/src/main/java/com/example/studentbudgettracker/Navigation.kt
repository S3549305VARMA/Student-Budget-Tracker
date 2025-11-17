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
        composable("add_expense") { AddExpenseScreen(navController) }
        composable("expense_list") { ExpenseListScreen(navController) }
=======
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
>>>>>>> ecceccd1e9a75e5a24599df572fdbbeff2d13697
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

>>>>>>> ecceccd1e9a75e5a24599df572fdbbeff2d13697
        }
    }
}