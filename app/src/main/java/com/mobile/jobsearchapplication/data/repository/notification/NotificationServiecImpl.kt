package com.mobile.jobsearchapplication.data.repository.notification

import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.network.ApiClient // Import đúng package
import com.mobile.jobsearchapplication.network.NotificationApiService

class NotificationServiceImpl(private val apiService: NotificationApiService) : NotificationService {

    override suspend fun getAllNotifications(): List<NotificationData> {
        val response = apiService.getAllNotifications()
        return response.data?.data ?: emptyList() // Trả về danh sách thông báo hoặc rỗng nếu null
    }
}