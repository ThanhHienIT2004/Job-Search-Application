package com.mobile.jobsearchapplication.data.repository.notification

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.NotificationListResponse
import com.mobile.jobsearchapplication.data.model.notification.Notification
import com.mobile.jobsearchapplication.data.remote.notification.NotificationApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient
import java.util.UUID

class NotificationRepository : NotificationApiService {
    private val api = RetrofitClient.notificationApiService

    override suspend fun getAllNotificationByUserId(
        userId: String,
        page: Int,
        limit: Int
    ): NotificationListResponse<Notification> {
        return api.getAllNotificationByUserId(userId, page, limit)
    }

    override suspend fun updateNotificationReadStatus(
        notificationId: String,
        isRead: Boolean
    ): ApiResponse<Notification> {
        return api.updateNotificationReadStatus(notificationId, isRead)
    }

    override suspend fun createNotification(notification: Notification): ApiResponse<Notification> {
        return try {
            // Kiểm tra dữ liệu bắt buộc
            if (notification.title.isNullOrEmpty() ||
                notification.type.isNullOrEmpty() ||
                notification.userId.isNullOrEmpty()) {
                return ApiResponse(
                    data = null,
                    message = "Title, type, và userId không được rỗng"
                )
            }

            // Gửi yêu cầu POST, server sẽ sinh Id tự tăng
            val response = api.createNotification(notification)
            if (response.data != null) {
                ApiResponse(
                    data = response.data,
                    message = response.message
                )
            } else {
                ApiResponse(
                    data = null,
                    message = response.message ?: "Failed to create notification"
                )
            }
        } catch (e: Exception) {
            ApiResponse(
                data = null,
                message = e.message ?: "Error creating notification"
            )
        }
    }

    override suspend fun deleteNotification(notificationId: UUID): ApiResponse<Notification> {
        TODO("Not yet implemented")
    }
}