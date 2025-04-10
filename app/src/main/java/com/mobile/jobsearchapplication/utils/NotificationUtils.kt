package com.mobile.jobsearchapplication.utils

import com.mobile.jobsearchapplication.data.model.notification.NotificationData
import kotlinx.datetime.*

object NotificationUtils {
    fun groupNotificationsByDate(notifications: List<NotificationData>): Map<String, List<NotificationData>> {
        return notifications.groupBy { getRelativeDate(it.time) }
    }

    fun getRelativeDate(date: Long): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val notificationDate = Instant.fromEpochMilliseconds(date).toLocalDateTime(TimeZone.currentSystemDefault()).date

        return when {
            notificationDate == now -> "Hôm nay"
            notificationDate == now.minus(1, DateTimeUnit.DAY) -> "Hôm qua"
            else -> "${notificationDate.dayOfMonth}/${notificationDate.monthNumber}/${notificationDate.year}"
        }
    }
    // ✅ Định dạng ngày giờ khi hiển thị thông báo
    fun formatDateTime(date: Long): String {
        val localDateTime = Instant.fromEpochMilliseconds(date).toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.dayOfMonth}/${localDateTime.monthNumber}/${localDateTime.year} ${localDateTime.hour}:${localDateTime.minute}"
    }
}




