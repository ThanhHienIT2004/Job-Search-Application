package com.mobile.jobsearchapplication.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.model.NotificationData

@Composable
fun NotificationSection(title: String, notifications: Map<String, List<NotificationData>>) {
    Column {
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
