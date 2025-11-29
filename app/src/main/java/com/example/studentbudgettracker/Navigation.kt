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
        composable("splash") { SplashScreen(navController) }
=======
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> dbc6bb516095880f9dab429df7537daffe46cc9d
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("add_expense") { AddExpenseScreen(navController) }
        composable("expense_list") { ExpenseListScreen(navController) }
<<<<<<< HEAD
        composable("statistics") { StatisticsScreen(navController) }
=======
=======

        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
>>>>>>> 35952a9cc352f589beb934e978aca9be70ab59cc
=======
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
<<<<<<< HEAD
        composable("dashboard") { DashboardScreen(navController) }
>>>>>>> ecceccd1e9a75e5a24599df572fdbbeff2d13697
=======

>>>>>>> d155b624ad3bfdeed38b2eb5673651b7353d91ce
>>>>>>> 35952a9cc352f589beb934e978aca9be70ab59cc
>>>>>>> dbc6bb516095880f9dab429df7537daffe46cc9d
        composable(
            "edit_expense/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> dbc6bb516095880f9dab429df7537daffe46cc9d
            EditExpenseScreen(
                navController,
                backStackEntry.arguments?.getInt("expenseId") ?: 0
            )
<<<<<<< HEAD
=======
=======

<<<<<<< HEAD
>>>>>>> ecceccd1e9a75e5a24599df572fdbbeff2d13697
=======
>>>>>>> d155b624ad3bfdeed38b2eb5673651b7353d91ce
>>>>>>> 35952a9cc352f589beb934e978aca9be70ab59cc
>>>>>>> dbc6bb516095880f9dab429df7537daffe46cc9d
        }
    }
}