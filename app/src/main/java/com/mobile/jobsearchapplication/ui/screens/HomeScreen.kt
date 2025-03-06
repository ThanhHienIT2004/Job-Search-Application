package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavigationBar

@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedTab) { selectedTab = it } } // ✅ Chỉ truyền `selectedTab`
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // ✅ Đã sử dụng paddingValues
                .padding(16.dp)
        ) {
            when (selectedTab) {
                0 -> Text(text = "🏠 Trang chủ", style = MaterialTheme.typography.titleLarge)
                1 -> Text(text = "➕ Đăng Tin", style = MaterialTheme.typography.titleLarge)
                2 -> Text(text = "🔔 Thông Báo", style = MaterialTheme.typography.titleLarge)
                3 -> UserScreen() // 👤 Màn hình tài khoản
            }
        }
    }
}
