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
    val typeNotification: String = ""
)

data class NotificationUserState(
    val notifications: List<SingleNotification> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMore: Boolean = true
)

class NotificationViewModel : ViewModel() {
    private val notificationRepository = NotificationRepository()
    private val limit = 10

    private val _notificationsState = MutableStateFlow(NotificationUserState())
    val notificationsState = _notificationsState.asStateFlow()

    fun loadNotification(userId: String) {
        if (_notificationsState.value.notifications.isNotEmpty()) return
        viewModelScope.launch {
            _notificationsState.update { it.copy(isLoading = true) }
            try {
                val response = notificationRepository.getAllNotificationByUserId(userId, 1, limit)
                val notifications = response.data?.map { it.toSingleNotification() } ?: emptyList()
                _notificationsState.update {
                    it.copy(
                        notifications = notifications,
                        isLoading = false,
                        error = null,
                        currentPage = 1,
                        hasMore = notifications.size == limit
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
        if (!_notificationsState.value.hasMore || _notificationsState.value.isLoadingMore) return
        viewModelScope.launch {
            _notificationsState.update { it.copy(isLoadingMore = true) }
            try {
                val nextPage = _notificationsState.value.currentPage + 1
                val response = notificationRepository.getAllNotificationByUserId(userId, nextPage, limit)
                val newNotifications = response.data?.map { it.toSingleNotification() } ?: emptyList()
                _notificationsState.update {
                    it.copy(
                        notifications = it.notifications + newNotifications,
                        isLoadingMore = false,
                        error = null,
                        currentPage = nextPage,
                        hasMore = newNotifications.size == limit
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
}