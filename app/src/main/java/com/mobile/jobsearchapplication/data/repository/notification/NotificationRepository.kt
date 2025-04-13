package com.mobile.jobsearchapplication.data.repository.notification

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.PaginatedData
import com.mobile.jobsearchapplication.data.model.notification.NotificationData
import com.mobile.jobsearchapplication.data.model.user.User
import com.mobile.jobsearchapplication.data.remote.notification.NotificationApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient
import java.util.UUID


class NotificationRepository : NotificationApiService {
    private val api = RetrofitClient.notificationApiService
    // Lấy tất cả thông báo
    override suspend fun getAllNotifications(): ApiResponse<PaginatedData<NotificationData>> {
        return api.getAllNotifications()
    }

    override suspend fun getNotificationById(notificationId: UUID): ApiResponse<NotificationData> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: UUID): ApiResponse<User> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNotification(notificationId: UUID): ApiResponse<NotificationData> {
        TODO("Not yet implemented")
    }
}