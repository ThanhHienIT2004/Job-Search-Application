package com.mobile.jobsearchapplication.data.model.notification

data class NotificationData(
    val imageRes: Int,
    val title: String,
    val description: String,
    val time: Long,
    val isRead: Boolean = false
)
