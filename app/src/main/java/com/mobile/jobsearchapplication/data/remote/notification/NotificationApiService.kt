package com.mobile.jobsearchapplication.data.remote.notification


import com.google.gson.annotations.SerializedName
import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.NotificationListResponse
import com.mobile.jobsearchapplication.data.model.PaginatedData
import com.mobile.jobsearchapplication.data.model.notification.Notification
import com.mobile.jobsearchapplication.data.model.user.User
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface   NotificationApiService {

    // Lấy tất cả thông báo
    @GET("notification/all")
    suspend fun getAllNotificationByUserId(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ):NotificationListResponse<Notification>

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