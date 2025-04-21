package com.mobile.jobsearchapplication.ui.components.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.features.notification.NotificationViewModel

@Composable
fun IconNotification(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = viewModel()
) {
    val notiState by viewModel.notificationsState.collectAsState()
    val unReadCount = notiState.unReadCount

    Box(
        modifier = modifier
            .size(36.dp)
            .clickable { navController.navigate("notification_screen") }
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications Icon",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        // Hiển thị badge nếu có thông báo chưa đọc
        if (unReadCount > 0) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 16.dp, y = (-4).dp)
                    .background(Color.Red, CircleShape)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (unReadCount > 9) "9+" else unReadCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}