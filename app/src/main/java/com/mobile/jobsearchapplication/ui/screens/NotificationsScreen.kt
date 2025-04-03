package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.ui.components.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.components.NotificationSection
import com.mobile.jobsearchapplication.utils.NotificationUtils
import com.mobile.jobsearchapplication.ui.viewmodel.NotificationViewModel
import com.mobile.jobsearchapplication.ui.base.BaseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationViewModel = viewModel()
) {
    val userNotifications by viewModel.userNotifications.collectAsState()
    val recruiterNotifications by viewModel.recruiterNotifications.collectAsState()

    val groupedUserNotifications = NotificationUtils.groupNotificationsByDate(userNotifications)
    val groupedRecruiterNotifications = NotificationUtils.groupNotificationsByDate(recruiterNotifications)

    BaseScreen(
        title = "Thông báo",
                actionsTop = {
            SearchBar(
                navController = navController,
                onMenuClicked = {
                    println("Menu clicked")
                }
            )
        },
        actionsBot = { BottomNavBarCustom(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .windowInsetsPadding(WindowInsets.statusBars),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NotificationSection(
                title = "Thông báo của bạn",
                notifications = groupedUserNotifications,
                modifier = Modifier.weight(1f)
            )

            Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))

            NotificationSection(
                title = "Thông báo từ nhà tuyển dụng",
                notifications = groupedRecruiterNotifications,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewNotificationsScreen() {
//    val fakeNavController = rememberNavController()
//    NotificationsScreen(navController = fakeNavController)
//}
