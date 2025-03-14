package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.screens.components.NotificationSection
import com.mobile.jobsearchapplication.utils.NotificationUtils
import com.mobile.jobsearchapplication.viewmodel.NotificationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController,viewModel: NotificationViewModel = viewModel()) {
    val userNotifications by viewModel.userNotifications.collectAsState()
    val recruiterNotifications by viewModel.recruiterNotifications.collectAsState()

    val groupedUserNotifications = NotificationUtils.groupNotificationsByDate(userNotifications)
    val groupedRecruiterNotifications = NotificationUtils.groupNotificationsByDate(recruiterNotifications)

    BaseScreen (
        actionsBot = { BottomNavBarCustom(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NotificationSection("Thông báo của bạn", groupedUserNotifications)
            Divider()
            NotificationSection("Thông báo từ nhà tuyển dụng", groupedRecruiterNotifications)
        }
    }
}
