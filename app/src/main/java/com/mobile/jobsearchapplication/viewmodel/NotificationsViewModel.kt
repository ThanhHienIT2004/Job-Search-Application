package com.mobile.jobsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.utils.NotificationUtils.getRelativeDate

class NotificationViewModel : ViewModel() {
    private val _userNotifications = MutableStateFlow(
        listOf(
            NotificationData(R.drawable.baseline_notifications_24, "Công việc mới", "Công ty ABC vừa đăng tin tuyển dụng", 1733175900000L, false),
            NotificationData(R.drawable.baseline_notifications_24, "Lời mời phỏng vấn", "Bạn có lời mời phỏng vấn từ Công ty DEF", 1733099400000L, true)
        )
    )
    val userNotifications: StateFlow<List<NotificationData>> = _userNotifications

    private val _recruiterNotifications = MutableStateFlow(
        listOf(
            NotificationData(R.drawable.baseline_notifications_24, "Nhà tuyển dụng đã xem hồ sơ", "Nhà tuyển dụng XYZ đã xem hồ sơ của bạn", 1733154600000L, false),
            NotificationData(R.drawable.baseline_notifications_24, "Tin tuyển dụng mới", "Công ty GHI đã đăng tin tuyển dụng mới", 1733000400000L, true)
        )
    )
    val recruiterNotifications: StateFlow<List<NotificationData>> = _recruiterNotifications

    // Đánh dấu thông báo là đã xem
    fun markAsRead(notification: NotificationData) {
        _userNotifications.value = _userNotifications.value.map {
            if (it == notification) it.copy(isRead = true) else it
        }
        _recruiterNotifications.value = _recruiterNotifications.value.map {
            if (it == notification) it.copy(isRead = true) else it
        }
    }

    // Đánh dấu tất cả thông báo là đã xem
    fun markAllAsRead() {
        _userNotifications.value = _userNotifications.value.map { it.copy(isRead = true) }
        _recruiterNotifications.value = _recruiterNotifications.value.map { it.copy(isRead = true) }
    }

    // Kiểm tra xem có thông báo chưa đọc nào không
    fun hasUnreadNotifications(): Boolean {
        return _userNotifications.value.any { !it.isRead } || _recruiterNotifications.value.any { !it.isRead }
    }

    fun groupNotificationsByDate(notifications: List<NotificationData>): Map<String, List<NotificationData>> {
        return notifications.groupBy { getRelativeDate(it.time) }
    }
}