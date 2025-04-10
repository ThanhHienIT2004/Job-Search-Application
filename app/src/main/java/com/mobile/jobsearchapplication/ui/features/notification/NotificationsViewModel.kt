package com.mobile.jobsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.data.repository.notification.NotificationService
import com.mobile.jobsearchapplication.data.repository.notification.NotificationServiceImpl
import com.mobile.jobsearchapplication.network.ApiClient
import com.mobile.jobsearchapplication.utils.NotificationUtils.getRelativeDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val notificationService: NotificationService = NotificationServiceImpl(ApiClient.notificationApiService)
) : ViewModel() {

    private val _userNotifications = MutableStateFlow<List<NotificationData>>(emptyList())
    val userNotifications: StateFlow<List<NotificationData>> = _userNotifications

    private val _recruiterNotifications = MutableStateFlow<List<NotificationData>>(emptyList())
    val recruiterNotifications: StateFlow<List<NotificationData>> = _recruiterNotifications

    init {
        fetchNotifications() // Tự động lấy dữ liệu khi ViewModel khởi tạo
    }

    // Lấy dữ liệu từ API
    private fun fetchNotifications() {
        viewModelScope.launch {
            try {
                val notifications = notificationService.getAllNotifications()
                // Phân loại thông báo dựa trên type
                _userNotifications.value = notifications.filter { it.type == "user" }
                _recruiterNotifications.value = notifications.filter { it.type == "recruiter" }
            } catch (e: Exception) {
                // Xử lý lỗi đơn giản: đặt danh sách rỗng
                _userNotifications.value = emptyList()
                _recruiterNotifications.value = emptyList()
            }
        }
    }

    // Đánh dấu một thông báo là đã đọc
    fun markAsRead(notification: NotificationData) {
        _userNotifications.value = _userNotifications.value.map {
            if (it == notification) it.copy(isRead = true) else it
        }
        _recruiterNotifications.value = _recruiterNotifications.value.map {
            if (it == notification) it.copy(isRead = true) else it
        }
    }

    // Đánh dấu tất cả thông báo là đã đọc
    fun markAllAsRead() {
        _userNotifications.value = _userNotifications.value.map { it.copy(isRead = true) }
        _recruiterNotifications.value = _recruiterNotifications.value.map { it.copy(isRead = true) }
    }

    // Kiểm tra xem có thông báo chưa đọc không
    fun hasUnreadNotifications(): Boolean {
        return _userNotifications.value.any { !it.isRead } || _recruiterNotifications.value.any { !it.isRead }
    }

    // Nhóm thông báo theo ngày
    fun groupNotificationsByDate(notifications: List<NotificationData>): Map<String, List<NotificationData>> {
        return notifications.groupBy { getRelativeDate(it.time) }
    }
}