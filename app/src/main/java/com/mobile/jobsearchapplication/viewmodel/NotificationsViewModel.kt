package com.mobile.jobsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.model.NotificationData
import com.mobile.jobsearchapplication.utils.DateUtils.getRelativeDate
import java.text.SimpleDateFormat
import java.util.*

class NotificationViewModel : ViewModel() {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    val userNotifications = listOf(
        NotificationData(R.drawable.baseline_notifications_24, "Công việc mới", "Công ty ABC vừa đăng tin tuyển dụng", dateFormat.parse("05/03/2025 12:45")!!),
        NotificationData(R.drawable.baseline_notifications_24, "Lời mời phỏng vấn", "Bạn có lời mời phỏng vấn từ Công ty DEF", dateFormat.parse("04/03/2025 18:00")!!)
    )

    val recruiterNotifications = listOf(
        NotificationData(R.drawable.baseline_notifications_24, "Nhà tuyển dụng đã xem hồ sơ", "Nhà tuyển dụng XYZ đã xem hồ sơ của bạn", dateFormat.parse("05/03/2025 10:30")!!),
        NotificationData(R.drawable.baseline_notifications_24, "Tin tuyển dụng mới", "Công ty GHI đã đăng tin tuyển dụng mới", dateFormat.parse("03/03/2025 14:20")!!)
    )

    fun groupNotificationsByDate(notifications: List<NotificationData>): Map<String, List<NotificationData>> {
        return notifications.groupBy { getRelativeDate(it.time) }
    }
}
