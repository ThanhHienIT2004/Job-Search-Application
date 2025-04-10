package com.mobile.jobsearchapplication.data.repository.notification

import com.mobile.jobsearchapplication.data.model.NotificationData

interface NotificationService {
    // Lấy tất cả thông báo
    suspend fun getAllNotifications(): List<NotificationData>
}