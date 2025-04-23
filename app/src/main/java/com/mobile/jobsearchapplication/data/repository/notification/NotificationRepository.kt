package com.mobile.jobsearchapplication.data.repository.notification

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.BaseResponse
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
        notificationId: Long,
        isRead: Boolean
    ): ApiResponse<Notification> {
        return api.updateNotificationReadStatus(notificationId, isRead)
    }

    override suspend fun createNotification(notification: Notification): BaseResponse<Notification> {
        return try {
            api.createNotification(notification)

        } catch (e: Exception){
            BaseResponse(isSuccess = false, message = e.message ?: "Unknown error")
        }

    }

    override suspend fun deleteNotification(notificationId: UUID): ApiResponse<Notification> {
        TODO("Not yet implemented")
    }
}