package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.viewmodel.UserViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val userNotifications = remember {
        mutableStateListOf(
            NotificationData(R.drawable.baseline_notifications_24, "Công việc mới", "Công ty ABC vừa đăng tin tuyển dụng", 1733175900000L),
            NotificationData(R.drawable.baseline_notifications_24, "Lời mời phỏng vấn", "Bạn có lời mời phỏng vấn từ Công ty DEF", 1733099400000L)
        )
    }

    val recruiterNotifications = remember {
        mutableStateListOf(
            NotificationData(R.drawable.baseline_notifications_24, "Nhà tuyển dụng đã xem hồ sơ", "Nhà tuyển dụng XYZ đã xem hồ sơ của bạn", 1733154600000L),
            NotificationData(R.drawable.baseline_notifications_24, "Tin tuyển dụng mới", "Công ty GHI đã đăng tin tuyển dụng mới", 1733000400000L)
        )
    }

    val groupedUserNotifications = groupNotificationsByDate(userNotifications)
    val groupedRecruiterNotifications = groupNotificationsByDate(recruiterNotifications)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Thông báo", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Thông báo của bạn (Trên)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                NotificationSection("Thông báo của bạn", groupedUserNotifications)
            }

            Divider(modifier = Modifier.height(1.dp)) // Đường kẻ phân cách

            // Thông báo từ nhà tuyển dụng (Dưới)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                NotificationSection("Thông báo từ nhà tuyển dụng", groupedRecruiterNotifications)
            }
        }
    }
}


@Composable
fun NotificationSection(title: String, notifications: Map<String, List<NotificationData>>) {
    Column{
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        LazyColumn {
            notifications.forEach { (dateHeader, items) ->
                item {
                    Text(
                        text = dateHeader,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                items(items) { notification ->
                    NotificationItem(notification)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData) {
    val formattedTime = formatDateTime(notification.time) // ✅ Gọi hàm định dạng thời gian

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = notification.imageRes),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 12.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(notification.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(notification.description, fontSize = 14.sp)
            Text(formattedTime, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}


// ✅ Hàm nhóm thông báo theo ngày
fun groupNotificationsByDate(notifications: List<NotificationData>): Map<String, List<NotificationData>> {
    return notifications.groupBy { getRelativeDate(it.time) }
}

// ✅ Hàm xác định ngày tương đối (Hôm nay, Hôm qua, ...)
fun getRelativeDate(date: Long): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val notificationDate = Instant.fromEpochMilliseconds(date).toLocalDateTime(TimeZone.currentSystemDefault()).date

    return when {
        notificationDate == now -> "Hôm nay"
        notificationDate == now.minus(1, DateTimeUnit.DAY) -> "Hôm qua"
        else -> "${notificationDate.dayOfMonth}/${notificationDate.monthNumber}/${notificationDate.year}"
    }
}

// ✅ Định dạng ngày giờ khi hiển thị thông báo
fun formatDateTime(date: Long): String {
    val localDateTime = Instant.fromEpochMilliseconds(date).toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.dayOfMonth}/${localDateTime.monthNumber}/${localDateTime.year} ${localDateTime.hour}:${localDateTime.minute}"
}
