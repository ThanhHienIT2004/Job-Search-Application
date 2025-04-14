package com.mobile.jobsearchapplication.data.remote.notification


import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.PaginatedData
import com.mobile.jobsearchapplication.data.model.notification.Notification
import com.mobile.jobsearchapplication.data.model.user.User
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface   NotificationApiService {

    // Lấy tất cả thông báo
    @GET("notification/all/{id}")
    suspend fun getAllNotifications(@Path("id") userId: String): ApiResponse<PaginatedData<Notification>>

    // Lấy thông báo theo ID
    @GET("get/{id}")
    suspend fun getNotificationById(@Path("id") notificationId: UUID): ApiResponse<Notification>

    // Lấy thông tin người dùng theo ID
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: UUID): ApiResponse<User>

    // Xóa thông báo theo ID
    @DELETE("delete/{id}")
    suspend fun deleteNotification(@Path("id") notificationId: UUID): ApiResponse<Notification>
}