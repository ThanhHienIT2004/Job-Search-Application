package com.mobile.jobsearchapplication.network

import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.data.model.User
import com.mobile.jobsearchapplication.network.responses.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface NotificationApiService {

    // Lấy tất cả thông báo
    @GET("all")
    suspend fun getAllNotifications(): ApiResponse<NotificationData>

    // Lấy thông báo theo ID
    @GET("get/{id}")
    suspend fun getNotificationById(@Path("id") notificationId: UUID): ApiResponse<NotificationData>

    // Lấy thông tin người dùng theo ID
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: UUID): ApiResponse<User>

    // Xóa thông báo theo ID
    @DELETE("delete/{id}")
    suspend fun deleteNotification(@Path("id") notificationId: UUID): ApiResponse<NotificationData>
}