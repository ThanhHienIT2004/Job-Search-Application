package com.mobile.jobsearchapplication.ui.screens.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.data.model.notification.NotificationData

@Composable
fun NotificationItem(notification: NotificationData) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp),
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
