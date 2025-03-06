package com.mobile.jobsearchapplication.model

import java.util.Date

data class NotificationData(
    val imageRes: Int,
    val title: String,
    val description: String,
    val time: Date
)