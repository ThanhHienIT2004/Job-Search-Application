package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.ui.components.bottom_bar.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.components.NotificationSection
import com.mobile.jobsearchapplication.utils.NotificationUtils
import com.mobile.jobsearchapplication.viewmodel.NotificationViewModel
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
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        showSearch = true,
        navController = navController,
        actionsBot = { BottomNavBarCustom(navController) },
        // Thêm nút đỏ trên TopBar
        actionsTop = {
            IconButton(onClick = { /* Xử lý sự kiện nút đỏ */ }) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color.Red, shape = CircleShape)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .windowInsetsPadding(WindowInsets.statusBars),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NotificationSection("Thông báo của bạn", groupedUserNotifications, viewModel, Modifier.weight(1f))
            Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
            NotificationSection("Thông báo từ nhà tuyển dụng", groupedRecruiterNotifications, viewModel, Modifier.weight(1f))
        }
    }
}
@Composable
fun NotificationSection(
    title: String,
    notifications: Map<String, List<NotificationData>>,
    viewModel: NotificationViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxHeight()) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
        notifications.forEach { (dateHeader, items) ->
            item {
                Text(text = dateHeader, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            items(items) { notification ->
                NotificationItem(notification, viewModel)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData, viewModel: NotificationViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (notification.isRead) Color.LightGray else Color.White) // Đậm nếu chưa xem
            .padding(13.dp)
            .pointerInput(Unit) {
                detectTapGestures { viewModel.markAsRead(notification) } // Đánh dấu đã xem khi nhấn
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = notification.imageRes),
            contentDescription = "Avatar",
            modifier = Modifier.size(48.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(notification.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(notification.description, fontSize = 14.sp)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewNotificationsScreen() {
    val fakeNavController = rememberNavController()
    NotificationsScreen(navController = fakeNavController)
}
