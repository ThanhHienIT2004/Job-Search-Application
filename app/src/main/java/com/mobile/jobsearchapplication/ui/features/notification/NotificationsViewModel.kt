package com.mobile.jobsearchapplication.ui.features.notification

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.notification.Notification
import com.mobile.jobsearchapplication.data.repository.notification.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SingleNotification(
    @DrawableRes val avatar: Int = 0,
    val title: String = "",
    val message: String = "",
    val createAt: kotlinx.datetime.LocalDateTime? = null,
    val senderID: String = "",
    val senderName: String = "",
    val typeNotification: String = ""
)

data class NotificationUserState(
    val notifications: List<SingleNotification> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class NotificationViewModel : ViewModel() {
    private val notificationRepository = NotificationRepository()

    private val _notificationsState = MutableStateFlow(NotificationUserState())
    val notificationsState = _notificationsState.asStateFlow()

    fun loadNotification(userId: String) {
        viewModelScope.launch {
            _notificationsState.update { it.copy(isLoading = true) }
            try {
                val response = notificationRepository.getAllNotifications(userId)
                val notifications = (response.data as? List<Notification>)?.map { it.toSingleNotification() } ?: emptyList()
                _notificationsState.update {
                    it.copy(
                        notifications = notifications,
                        isLoading = false,
                        error = null
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
}