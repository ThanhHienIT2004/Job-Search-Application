package com.mobile.jobsearchapplication.data.model.notification

import androidx.core.app.NotificationCompat.MessagingStyle.Message

data class NotificationData(
    val imageRes: Int,
    val title: String,
    val type:String,
    val description: String,
    val time: Long,
    val isRead: Boolean = false
)
data class NotificationReponse<T>(
    val data: NotificationData,
    val message: String
)