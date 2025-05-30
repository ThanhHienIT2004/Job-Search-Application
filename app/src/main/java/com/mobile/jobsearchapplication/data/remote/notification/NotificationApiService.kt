package com.mobile.jobsearchapplication.data.remote.notification


import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.NotificationListResponse
import com.mobile.jobsearchapplication.data.model.notification.Notification
import com.mobile.jobsearchapplication.data.model.user.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
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

    // Trong NotificationApiService.kt
    @PATCH("notification/{id}/read")
    suspend fun updateNotificationReadStatus(
        @Path("id") notificationId: Long,
        @Body isRead: Boolean
    ): ApiResponse<Notification>

    // Tạo thông báo
    @POST("notification/add")
    suspend fun createNotification(@Body notification: Notification): BaseResponse<Notification>

    // Xóa thông báo theo ID
    @DELETE("delete/{id}")
    suspend fun deleteNotification(@Path("id") notificationId: UUID): ApiResponse<Notification>
}