package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavigationBar
import com.mobile.jobsearchapplication.ui.screens.components.MenuList
import com.mobile.jobsearchapplication.viewmodel.UserViewModel

@Composable
fun UserScreen(viewModel: UserViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(3) } // Mặc định tab tài khoản

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedTab) { selectedTab = it } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (selectedTab) {
                0 -> HomeScreen() // Quay về màn hình Home
                1 -> Text(text = "➕ Đăng Tin", style = MaterialTheme.typography.titleLarge)
                2 -> Text(text = "🔔 Thông Báo", style = MaterialTheme.typography.titleLarge)
                3 -> {
                    Text(text = "👤 Tài khoản", style = MaterialTheme.typography.titleLarge)
                    MenuList(viewModel)
                }
            }
        }
    }
}
