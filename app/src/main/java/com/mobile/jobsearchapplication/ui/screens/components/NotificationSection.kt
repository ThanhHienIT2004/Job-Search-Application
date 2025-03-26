package com.mobile.jobsearchapplication.ui.screens.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.ui.screens.notification.NotificationItem

@Composable
fun NotificationSection(title: String, notifications: Map<String, List<NotificationData>>) {
    LazyColumn {
        item {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        notifications.forEach { (dateHeader, items) ->
            item {
                Text(text = dateHeader, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            items(items) { notification ->
                NotificationItem(notification)
            }
        }
    }
}
