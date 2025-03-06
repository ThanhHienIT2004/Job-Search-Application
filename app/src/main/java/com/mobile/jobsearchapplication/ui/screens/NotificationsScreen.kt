package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.screens.components.NotificationSection
import com.mobile.jobsearchapplication.viewmodel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController, viewModel: NotificationViewModel = viewModel()) {
    val groupedUserNotifications = viewModel.groupNotificationsByDate(viewModel.userNotifications)
    val groupedRecruiterNotifications = viewModel.groupNotificationsByDate(viewModel.recruiterNotifications)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Thông báo", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NotificationSection("Thông báo của bạn", groupedUserNotifications)
            Divider(modifier = Modifier.height(1.dp))
            NotificationSection("Thông báo từ nhà tuyển dụng", groupedRecruiterNotifications)
        }
    }
}
