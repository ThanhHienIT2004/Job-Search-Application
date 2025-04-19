package com.mobile.jobsearchapplication.ui.features.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.MainActivity
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.notification.Notification
import com.mobile.jobsearchapplication.data.repository.notification.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class SingleNotification(
    val id: Comparable<*>, // Thêm id kiểu Long
    @DrawableRes val avatar: Int = 0,
    val title: String = "",
    val description: String = "",
    val createdAt: String? = null,
    val senderID: String = "",
    val userId: String = "",
    val senderName: String = "",
    val type: String = "",
    val isRead: Boolean = false,
    val jobId: String? = null
)

data class NotificationUserState(
    val notifications: List<SingleNotification> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMore: Boolean = true,
    val unReadCount: Int = 0
)

class NotificationViewModel : ViewModel() {
    private val notificationRepository = NotificationRepository()
    private val limit = 10
    private val channelId = "JobSearchAppNotifications"

    private val _notificationsState = MutableStateFlow(NotificationUserState())
    val notificationsState = _notificationsState.asStateFlow()

    private fun updateNotificationsAndCount(
        currentState: NotificationUserState,
        updateAction: (List<SingleNotification>) -> List<SingleNotification>
    ): Pair<List<SingleNotification>, Int> {
        val updatedNotifications = updateAction(currentState.notifications)
        val unReadCount = updatedNotifications.count { !it.isRead }
        return updatedNotifications to unReadCount
    }

    fun loadNotification(userId: String) {
        if (_notificationsState.value.notifications.isNotEmpty()) return
        viewModelScope.launch {
            _notificationsState.update { it.copy(isLoading = true) }
            try {
                val response = notificationRepository.getAllNotificationByUserId(userId, 1, limit)
                val notifications = response.data?.map { it.toSingleNotification() } ?: emptyList()
                val unReadCount = notifications.count { !it.isRead }
                _notificationsState.update {
                    it.copy(
                        notifications = notifications,
                        isLoading = false,
                        error = null,
                        currentPage = 1,
                        hasMore = notifications.size == limit,
                        unReadCount = unReadCount // Sửa để dùng unReadCount tính động
                    )
                }
            } catch (e: Exception) {
                _notificationsState.update {
                    it.copy(
                        notifications = emptyList(),
                        isLoading = false,
                        error = e.message ?: "Failed to load notifications"
                    )
                }
            }
        }
    }

    fun loadMoreNotifications(userId: String) {
        viewModelScope.launch {
            _notificationsState.update { it.copy(isLoadingMore = true) }
            try {
                val nextPage = _notificationsState.value.currentPage + 1
                val response = notificationRepository.getAllNotificationByUserId(userId, nextPage, limit)
                val newNotifications = response.data?.map { it.toSingleNotification() } ?: emptyList()
                val (updatedNotifications, unReadCount) = updateNotificationsAndCount(_notificationsState.value) { notifications ->
                    notifications + newNotifications
                }
                _notificationsState.update {
                    it.copy(
                        notifications = updatedNotifications,
                        isLoadingMore = false,
                        error = null,
                        currentPage = nextPage,
                        hasMore = newNotifications.size == limit,
                        unReadCount = unReadCount // Sửa để dùng unReadCount tính động
                    )
                }
            } catch (e: Exception) {
                _notificationsState.update {
                    it.copy(
                        isLoadingMore = false,
                        error = e.message ?: "Failed to load more notifications"
                    )
                }
            }
        }
    }

    fun checkIsRead(notificationId: String, isRead: Boolean) {
        viewModelScope.launch {
            try {
                // Gọi API để cập nhật server
                val response = notificationRepository.updateNotificationReadStatus(notificationId, isRead)
                if (response.data != null) {
                    _notificationsState.update { currentState ->
                        val (updatedNotifications, unReadCount) = updateNotificationsAndCount(currentState) { notifications ->
                            notifications.map { notification ->
                                if (notification.id.toString() == notificationId) {
                                    notification.copy(isRead = isRead)
                                } else {
                                    notification
                                }
                            }
                        }
                        currentState.copy(
                            notifications = updatedNotifications,
                            unReadCount = unReadCount
                        )
                    }
                } else {
                    _notificationsState.update { it.copy(error = response.message ?: "Không thể cập nhật trạng thái") }
                }
            } catch (e: Exception) {
                _notificationsState.update { it.copy(error = "Lỗi: ${e.message}") }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendApplicationNotification(
        context: Context,
        applicantId: String,
        jobId: String,
        jobTitle: String,
        receiverId: String,
        notificationId: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
    ) {
        viewModelScope.launch {
            try {
                val notification = Notification(
                    Id = null, // Server sẽ sinh Id tự tăng
                    userId = receiverId,
                    title = "Bạn Người dùng đã ứng tuyển công việc của bạn",
                    description = "Xem chi tiết công việc \"$jobTitle\" để biết thêm thông tin.",
                    type = "APPLICATION",
                    avatar = 0,
                    createdAt = java.time.LocalDateTime.now().toString(),
                    senderID = applicantId,
                    senderName = "Người dùng",
                    isRead = false,
                    relatedId = jobId
                )

                val response = withContext(Dispatchers.IO) {
                    notificationRepository.createNotification(notification)
                }

                if (response.data != null) {
                    withContext(Dispatchers.IO) {
                        sendPushNotification(receiverId, notification.title ?: "", notification.description ?: "", jobId)
                    }

                    val intent = Intent(context, MainActivity::class.java).apply {
                        action = Intent.ACTION_VIEW
                        data = android.net.Uri.parse("jobsearchapp://job_detail_screen/$jobId")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    val pendingIntent = PendingIntent.getActivity(
                        context,
                        notificationId,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    sendNotification(
                        context = context,
                        notificationId = notificationId,
                        title = notification.title ?: "",
                        content = notification.description ?: "",
                        pendingIntent = pendingIntent
                    )
                } else {
                    _notificationsState.update {
                        it.copy(error = response.message ?: "Failed to create notification")
                    }
                }
            } catch (e: Exception) {
                _notificationsState.update {
                    it.copy(error = "Lỗi khi gửi thông báo ứng tuyển: ${e.message}")
                }
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Job Search Notifications"
            val descriptionText = "Notifications for job search application"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(
        context: Context,
        notificationId: Int,
        title: String,
        content: String,
        avatarRes: Int? = null, // Add avatar resource
        pendingIntent: PendingIntent? = null
    ) {
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(avatarRes ?: R.drawable.detail) // Use avatar if provided
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(content)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        pendingIntent?.let {
            builder.setContentIntent(it)
        }

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    private fun sendPushNotification(receiverId: String, title: String, body: String, jobId: String) {
        // Giả định server-side logic để gửi thông báo đẩy qua FCM
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun testSendLocalNotification(context: Context) {
        sendApplicationNotification(
            context = context,
            applicantId = "test-applicant-123",
            jobId = "job-456",
            jobTitle = "Software Engineer",
            receiverId = "test-receiver-789"
        )
    }
}