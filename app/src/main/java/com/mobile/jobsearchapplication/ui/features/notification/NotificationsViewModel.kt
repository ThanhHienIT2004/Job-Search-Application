package com.mobile.jobsearchapplication.ui.features.notification

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.repository.notification.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SingleNotification(
    @DrawableRes val avatar: Int = 0,
    val title: String = "",
    val message: String = "",
    val createAt:String? = null,
    val senderID: String = "",
    val senderName: String = "",
    val typeNotification: String = "",
    val isRead:Boolean = false
)

data class NotificationUserState(
    val notifications: List<SingleNotification> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMore: Boolean = true,
    val unReadCount:Int = 0
)

class NotificationViewModel : ViewModel() {
    private val notificationRepository = NotificationRepository()
    private val limit = 10

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
                        unReadCount = 6
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
                val (updatedNotifications, unReadCount) = updateNotificationsAndCount(
                    NotificationUserState()
                ) { notifications ->
                    notifications + newNotifications
                }
                _notificationsState.update {
                    it.copy(
                        notifications = it.notifications + newNotifications,
                        isLoadingMore = false,
                        error = null,
                        currentPage = nextPage,
                        hasMore = newNotifications.size == limit,
                        unReadCount = 5
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
            _notificationsState.update { currentState ->
                val (updatedNotifications, unReadCount) = updateNotificationsAndCount(currentState) { notifications ->
                    notifications.map { notification ->
                        if (notification.senderID == notificationId) {
                            notification.copy(isRead = isRead)
                        } else {
                            notification
                        }
                    }
                }
                currentState.copy(notifications = updatedNotifications)
            }
        }
    }
}