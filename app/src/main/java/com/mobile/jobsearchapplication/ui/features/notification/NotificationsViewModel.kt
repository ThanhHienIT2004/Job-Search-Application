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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.net.toUri
import com.mobile.jobsearchapplication.utils.FireBaseUtils
import java.util.UUID

data class SingleNotification(
    val id: Any,
    val avatar: String = "",
    val title: String = "",
    val description: String = "",
    val createdAt: String? = null,
    val senderID: String = "",
    val userId: String = "",
    val senderName: String = "",
    val type: String = "",
    val isRead: Boolean = false,
    val relateId: String? = null
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
    private val userId = FireBaseUtils.getLoggedInUserId()

    private val _notificationsState = MutableStateFlow(NotificationUserState())
    val notificationsState = _notificationsState.asStateFlow()



    private fun updateNotificationsAndCount(
        currentState: NotificationUserState,
        updateAction: (List<SingleNotification>) -> List<SingleNotification>
    ): Pair<List<SingleNotification>, Int> {
        val updatedNotifications = updateAction(currentState.notifications)
        val unReadCount = updatedNotifications.count { !it.isRead }
        println("Updated notifications: $updatedNotifications, unReadCount: $unReadCount")
        return updatedNotifications to unReadCount
    }

    fun createNotification(receiveId: String){
        viewModelScope.launch {
            val request = Notification(
                userId = receiveId,
                title = "Bạn có thông báo mới",
                description = "Người dùng khác đã ứng tuyển bài đăng của bạn",
                type = "Ứng tuyển",
                avatar = "0",
                createdAt ="",
                senderID = FireBaseUtils.getLoggedInUserId(),
                senderName = "Người dùng",
                isRead = false,
                relatedId =""
            )
            notificationRepository.createNotification(request)
        }
    }

    fun loadNotification(userId: String) {
        viewModelScope.launch {
            _notificationsState.update { it.copy(isLoading = true) }
            try {
                val response = notificationRepository.getAllNotificationByUserId(userId, 1, limit)
                val notifications = response.data?.map { it.toSingleNotification() } ?: emptyList()
                println("API response: $response, Notifications: $notifications")
                val unReadCount = notifications.count { !it.isRead }
                _notificationsState.update {
                    it.copy(
                        notifications = notifications,
                        isLoading = false,
                        error = null,
                        currentPage = 1,
                        hasMore = notifications.size == limit,
                        unReadCount = unReadCount
                    )
                }
            } catch (e: Exception) {
                println("Error loading notifications: ${e.message}")
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

    fun refreshNotifications(userId: String) {
        viewModelScope.launch {
            _notificationsState.update { it.copy(isLoading = true, notifications = emptyList()) }
            try {
                val response = notificationRepository.getAllNotificationByUserId(userId, 1, limit)
                val notifications = response.data?.map { it.toSingleNotification() } ?: emptyList()
                println("Refresh API response: $response, Notifications: $notifications")
                val unReadCount = notifications.count { !it.isRead }
                _notificationsState.update {
                    it.copy(
                        notifications = notifications,
                        isLoading = false,
                        error = null,
                        currentPage = 1,
                        hasMore = notifications.size == limit,
                        unReadCount = unReadCount
                    )
                }
            } catch (e: Exception) {
                println("Error refreshing notifications: ${e.message}")
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

    fun startPolling(userId: String) {
        viewModelScope.launch {
            while (true) {
                loadNotification(userId)
                delay(10000) // Polling mỗi 10 giây
            }
        }
    }

    fun markAllAsRead(userId: String) {
        viewModelScope.launch {
            try {
                // Lấy danh sách thông báo chưa đọc
                val unreadNotifications = _notificationsState.value.notifications.filter { !it.isRead }
                println("Marking all as read: $unreadNotifications")

                // Gọi API để cập nhật isRead = true cho từng thông báo
                for (notification in unreadNotifications) {
                    val response = notificationRepository.updateNotificationReadStatus(notification.id as Long, true)
                    if (response.data == null) {
                        println("Error marking notification ${notification.id} as read: ${response.message}")
                        _notificationsState.update { it.copy(error = response.message) }
                        return@launch
                    }
                }

                // Cập nhật state
                _notificationsState.update { currentState ->
                    val (updatedNotifications, unReadCount) = updateNotificationsAndCount(currentState) { notifications ->
                        notifications.map { it.copy(isRead = true) }
                    }
                    println("All notifications marked as read, unReadCount: $unReadCount")
                    currentState.copy(
                        notifications = updatedNotifications,
                        unReadCount = unReadCount
                    )
                }
            } catch (e: Exception) {
                println("Error marking all as read: ${e.message}")
                _notificationsState.update { it.copy(error = "Lỗi: ${e.message}") }
            }
        }
    }

    fun checkIsRead(notificationId: Long, isRead: Boolean) {
        viewModelScope.launch {
            try {
                val response = notificationRepository.updateNotificationReadStatus(notificationId, isRead)
                if (response.data != null) {
                    _notificationsState.update { currentState ->
                        val (updatedNotifications, unReadCount) = updateNotificationsAndCount(currentState) { notifications ->
                            notifications.map { notification ->
                                if (notification.id == notificationId) {
                                    notification.copy(isRead = isRead)
                                } else {
                                    notification
                                }
                            }
                        }
                        println("Check isRead: $notificationId, unReadCount: $unReadCount")
                        currentState.copy(
                            notifications = updatedNotifications,
                            unReadCount = unReadCount
                        )
                    }
                } else {
                    println("Error checking isRead: ${response.message}")
                    _notificationsState.update { it.copy(error = response.message) }
                }
            } catch (e: Exception) {
                println("Error checking isRead: ${e.message}")
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
                    Id = null,
                    userId = receiverId,
                    title = "Bạn Người dùng đã ứng tuyển công việc của bạn",
                    description = "Xem chi tiết công việc \"$jobTitle\" để biết thêm thông tin.",
                    type = "APPLICATION",
                    avatar = "0",
                    createdAt = java.time.LocalDateTime.now().toString(),
                    senderID = applicantId,
                    senderName = "Người dùng",
                    isRead = false,
                    relatedId = jobId
                )

                val response = withContext(Dispatchers.IO) {
                    notificationRepository.createNotification(notification)
                }

                if(response.data != null) {
//                    if (receiverId == _notificationsState.value.notifications.firstOrNull()?.userId) {
//                        _notificationsState.update { currentState ->
//                            val updatedNotifications = listOf(newNotification) + currentState.notifications
//                            val unReadCount = updatedNotifications.count { !it.isRead }
//                            currentState.copy(
//                                notifications = updatedNotifications,
//                                unReadCount = unReadCount
//                            )
//                        }
//                    }
                } else {
                    println("Error sending application notification: ${response.message}")
                    _notificationsState.update {
                        it.copy(error = response.message)
                    }}
            } catch (e: Exception) {
                println("Error sending application notification: ${e.message}")
                _notificationsState.update {
                    it.copy(error = "Lỗi khi gửi thông báo ứng tuyển: ${e.message}")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendLikeNotification(
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
                    Id = null,
                    userId = receiverId,
                    title = "Bạn Người dùng đã thích công việc của bạn",
                    description = "Xem chi tiết công việc \"$jobTitle\" để biết thêm thông tin.",
                    type = "LIKE",
                    avatar = "0",
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
                        data = "jobsearchapp://job_detail_screen/$jobId".toUri()
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
                    println("Error sending like notification: ${response.message}")
                    _notificationsState.update {
                        it.copy(error = response.message)
                    }
                }
            } catch (e: Exception) {
                println("Error sending like notification: ${e.message}")
                _notificationsState.update {
                    it.copy(error = "Lỗi khi gửi thông báo thích: ${e.message}")
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
        avatarRes: Int? = null,
        pendingIntent: PendingIntent? = null
    ) {
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(avatarRes ?: R.drawable.detail)
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
        // Giả định server-side logic để gửi thông báo đẩy
    }
}