package com.example.studentbudgettracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.studentbudgettracker.data.AppDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = AppDatabase.getDatabase(context)
    val userId = getUserId(context)

    var categoryData by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }
    var totalSpent by remember { mutableStateOf(0.0) }

    LaunchedEffect(key1 = true) {
        scope.launch {
            val expenses = database.expenseDao().getExpensesByUser(userId)
            totalSpent = expenses.sumOf { it.amount }
            categoryData = expenses.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistics", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9C27B0),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Spending by Category",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9C27B0)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Total Spent: £%.2f".format(totalSpent),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF5722)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (categoryData.isNotEmpty()) {
                        val slices = categoryData.entries.mapIndexed { index, entry ->
                            PieChartData.Slice(
                                label = entry.key,
                                value = entry.value.toFloat(),
                                color = getCategoryColor(entry.key)
                            )
                        }

                        val pieChartData = PieChartData(
                            slices = slices,
                            plotType = PlotType.Pie
                        )

                        val pieChartConfig = PieChartConfig(
                            isAnimationEnable = true,
                            showSliceLabels = true,
                            animationDuration = 1000,
                            labelVisible = true,
                            strokeWidth = 40f
                        )

                        PieChart(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            pieChartData = pieChartData,
                            pieChartConfig = pieChartConfig
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Category breakdown
                        categoryData.entries.sortedByDescending { it.value }.forEach { entry ->
                            CategoryBreakdownItem(
                                category = entry.key,
                                amount = entry.value,
                                percentage = (entry.value / totalSpent * 100).toFloat(),
                                color = getCategoryColor(entry.key)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    } else {
                        Text(
                            text = "No expenses to display",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryBreakdownItem(
    category: String,
    amount: Double,
    percentage: Float,
    color: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = category,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "£%.2f (%.1f%%)".format(amount, percentage),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = percentage / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}