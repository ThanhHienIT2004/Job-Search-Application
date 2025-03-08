package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen() {
    var jobTitle by remember { mutableStateOf(TextFieldValue()) }
    var companyName by remember { mutableStateOf(TextFieldValue()) }
    var jobDescription by remember { mutableStateOf(TextFieldValue()) }
    var location by remember { mutableStateOf(TextFieldValue()) }
    var salary by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Đăng tin tuyển dụng") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent, // Đặt trong suốt để hiển thị Gradient
                    titleContentColor = Color.White // Màu chữ trắng

                ),
                modifier = Modifier
                    .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Blue, Color.Red) // Gradient từ xanh sang cyan
                    )
                )
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = jobTitle,
                onValueChange = { jobTitle = it },
                label = { Text("Tiêu đề công việc") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text("Tên công ty") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = jobDescription,
                onValueChange = { jobDescription = it },
                label = { Text("Mô tả công việc") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Địa điểm làm việc") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = salary,
                onValueChange = { salary = it },
                label = { Text("Mức lương (VNĐ)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Xử lý đăng tin
                },
                modifier = Modifier.fillMaxWidth().wrapContentWidth()
            ) {
                Text("Đăng tin")
            }
        }
    }
}
