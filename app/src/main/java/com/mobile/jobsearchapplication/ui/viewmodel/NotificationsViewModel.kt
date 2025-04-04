package com.mobile.jobsearchapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.NotificationData
import com.mobile.jobsearchapplication.utils.NotificationUtils.getRelativeDate



class NotificationViewModel : ViewModel() {
    private val _userNotifications = MutableStateFlow(
        listOf(
            NotificationData(R.drawable.baseline_notifications_24, "Công việc mới", "Công ty ABC vừa đăng tin tuyển dụng", 1733175900000L),
            NotificationData(R.drawable.baseline_notifications_24, "Lời mời phỏng vấn", "Bạn có lời mời phỏng vấn từ Công ty DEF", 1733099400000L)
        )
    )
    val userNotifications: StateFlow<List<NotificationData>> = _userNotifications

    private val _recruiterNotifications = MutableStateFlow(
        listOf(
            NotificationData(R.drawable.baseline_notifications_24, "Nhà tuyển dụng đã xem hồ sơ", "Nhà tuyển dụng XYZ đã xem hồ sơ của bạn", 1733154600000L),
            NotificationData(R.drawable.baseline_notifications_24, "Tin tuyển dụng mới", "Công ty GHI đã đăng tin tuyển dụng mới", 1733000400000L)
        )
    )
    val recruiterNotifications: StateFlow<List<NotificationData>> = _recruiterNotifications

    fun groupNotificationsByDate(notifications: List<NotificationData>): Map<String, List<NotificationData>> {
        return notifications.groupBy { getRelativeDate(it.time) }
    }

}





