package com.mobile.jobsearchapplication.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.ui.screens.notification.NotificationItem

@Composable
fun NotificationSection(
    title: String,
    notifications: Map<String, List<NotificationData>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight() // Giãn đều trong phần chứa nó
    ) {
        item {
            // Căn giữa tiêu đề
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center // Căn giữa nội dung của Text
                )
            }
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

