package com.mobile.jobsearchapplication.ui.features.notification

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.mobile.jobsearchapplication.data.model.notification.NotificationData
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBarCustom
import com.mobile.jobsearchapplication.utils.NotificationUtils
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

    // Giả lập trạng thái có thông báo chưa đọc để test
    val hasUnreadNotifications by remember { mutableStateOf(true) } // Luôn true để hiện chấm đỏ

    // Khi có dữ liệu thực tế, thay bằng:
    // val hasUnreadNotifications by remember { derivedStateOf { viewModel.hasUnreadNotifications() } }
    LaunchedEffect(Unit) {
        viewModel.markAllAsRead()
    }
    BaseScreen(
        actionsBot = {
            BottomNavBarCustom(
                navController = navController
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE3F2FD), Color.White)
                    )
                )
                .padding(paddingValues)
                .windowInsetsPadding(WindowInsets.statusBars)

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(0.dp),

            ) {
                NotificationSection(
                    "Thông báo của bạn",
                    groupedUserNotifications,
                    viewModel,
                    Modifier.weight(1f)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = Color.Gray.copy(alpha = 0.3f)
                )
                NotificationSection(
                    "Thông báo từ nhà tuyển dụng",
                    groupedRecruiterNotifications,
                    viewModel,
                    Modifier.weight(1f)
                )
            }
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
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color(0xFFBBDEFB),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(12.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1976D2)
                )
            }
        }
        notifications.forEach { (dateHeader, items) ->
            item {
                Text(
                    text = dateHeader,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
            items(items) { notification ->
                NotificationItem(notification, viewModel)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData, viewModel: NotificationViewModel) {
    val backgroundColor by animateColorAsState(
        targetValue = if (notification.isRead) Color(0xFFF5F5F5) else Color.White
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .pointerInput(Unit) {
                detectTapGestures { viewModel.markAsRead(notification) }
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = painterResource(id = notification.imageRes),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                if (!notification.isRead) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.TopEnd)
                            .background(Color.Red, CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if (!notification.isRead) Color(0xFF1976D2) else Color.Black
                )
                Text(
                    text = notification.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationsScreen() {
    val fakeNavController = rememberNavController()
    NotificationsScreen(navController = fakeNavController)
}