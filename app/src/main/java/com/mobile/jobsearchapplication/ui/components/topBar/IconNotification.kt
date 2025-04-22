package com.mobile.jobsearchapplication.ui.components.topBar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile.jobsearchapplication.ui.features.notification.NotificationScreen
import com.mobile.jobsearchapplication.ui.features.notification.NotificationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IconNotification(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = viewModel()
) {
    val notificationState by viewModel.notificationsState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val userId = "g3DCV3byPnau4HXRhQYso4iTBoE2"

    // Debug unReadCount
    LaunchedEffect(notificationState.unReadCount) {
        println("IconNotification - unReadCount: ${notificationState.unReadCount}")
    }

    // Khởi động polling khi composable được tạo
    DisposableEffect(Unit) {
        viewModel.startPolling(userId)
        onDispose {
            // Không cần hủy coroutine vì ViewModelScope tự quản lý
        }
    }

    Box(
        modifier = modifier
            .size(36.dp)
            .clickable {
                viewModel.markAllAsRead(userId) // Đánh dấu tất cả là đã đọc
                showDialog = true
            }
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications Icon",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        // Hiển thị badge nếu có thông báo chưa đọc
        if (notificationState.unReadCount > 0) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 16.dp, y = (-4).dp)
                    .background(Color.Red, CircleShape)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (notificationState.unReadCount > 9) "9+" else notificationState.unReadCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Thông Báo",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        IconButton(onClick = { showDialog = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    NotificationScreen(
                        navController = null,
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}