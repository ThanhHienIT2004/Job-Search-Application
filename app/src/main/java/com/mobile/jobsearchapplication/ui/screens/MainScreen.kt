package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavigationBar

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedTab) { selectedTab = it } }
    ) { paddingValues ->
        // Dùng Modifier.padding(paddingValues) để áp dụng khoảng cách từ Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Đã sử dụng paddingValues
                .padding(16.dp) // Thêm padding bổ sung nếu cần
        ) {
            when (selectedTab) {
                0 -> Text(text = "🏠 Trang chủ", style = MaterialTheme.typography.titleLarge)
                1 -> PostScreen() // ➕ Đăng Tin
                2 -> NotificationScreen() // 🔔 Thông Báo
                3 -> UserScreen() // 👤 Tài Khoản
            }
        }
    }
}
