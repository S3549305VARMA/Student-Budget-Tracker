package com.example.studentbudgettracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studentbudgettracker.data.AppDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = AppDatabase.getDatabase(context)
    val userId = getUserId(context)

    var username by remember { mutableStateOf("User") }
    var weeklyBudget by remember { mutableStateOf(0.0) }
    var totalSpent by remember { mutableStateOf(0.0) }
    var remainingBudget by remember { mutableStateOf(0.0) }

    LaunchedEffect(key1 = true) {
        scope.launch {
            val user = database.userDao().getUserById(userId)
            username = user?.username ?: "User"
            weeklyBudget = user?.weeklyBudget ?: 0.0
            totalSpent = database.expenseDao().getTotalSpent(userId) ?: 0.0
            remainingBudget = weeklyBudget - totalSpent
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate("login") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, "Logout", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
        ) {
            // Budget Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF2196F3),
                                    Color(0xFF9C27B0)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            text = "Welcome, $username!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Weekly Budget", fontSize = 14.sp, color = Color.White.copy(0.8f))
                                Text("£%.2f".format(weeklyBudget), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Spent", fontSize = 14.sp, color = Color.White.copy(0.8f))
                                Text("£%.2f".format(totalSpent), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        LinearProgressIndicator(
                            progress = if (weeklyBudget > 0) (totalSpent / weeklyBudget).toFloat().coerceIn(0f, 1f) else 0f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = Color.White,
                            trackColor = Color.White.copy(0.3f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Remaining: £%.2f".format(remainingBudget),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (remainingBudget >= 0) Color.White else Color(0xFFFF5252)
                        )
                    }
                }
            }

            // Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                DashboardButton(
                    title = "Add Expense",
                    subtitle = "Record a new expense",
                    icon = Icons.Default.Add,
                    gradientColors = listOf(Color(0xFF4CAF50), Color(0xFF8BC34A)),
                    onClick = { navController.navigate("add_expense") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                DashboardButton(
                    title = "All Expenses",
                    subtitle = "View, edit, and delete",
                    icon = Icons.Default.List,
                    gradientColors = listOf(Color(0xFFFF9800), Color(0xFFFFB74D)),
                    onClick = { navController.navigate("expense_list") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                DashboardButton(
                    title = "Statistics",
                    subtitle = "View spending charts",
                    icon = Icons.Default.PieChart,
                    gradientColors = listOf(Color(0xFF9C27B0), Color(0xFFBA68C8)),
                    onClick = { navController.navigate("statistics") }
                )
            }
        }
    }
}

@Composable
fun DashboardButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(gradientColors))
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}