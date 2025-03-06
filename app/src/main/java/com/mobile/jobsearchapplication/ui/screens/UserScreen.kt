package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.viewmodel.UserViewModel
import com.mobile.jobsearchapplication.ui.screens.components.*

@Composable
fun UserScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = { BottomNavigationBar(viewModel, selectedTab) { selectedTab = it } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Header()
            // ✅ Truyền navController vào MenuList
            MenuList(viewModel, navController)
        }
    }
}
