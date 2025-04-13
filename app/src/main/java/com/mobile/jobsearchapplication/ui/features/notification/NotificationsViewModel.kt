package com.mobile.jobsearchapplication.ui.features.notification

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.notification.NotificationData
import com.mobile.jobsearchapplication.data.repository.notification.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

data class NotificationUserState(
    @DrawableRes val avatar: Int = 0,
    val title: String = "",
    val message: String = "",
    val createAt: LocalDateTime? = null,
    val senderID: String = "",  // id
    val senderName: String = "", //
    val typeNotification: String = "", // ADMIN, COMPANY, CHATTING
)

class NotificationViewModel(
) : ViewModel() {
    private val notificationRepository = NotificationRepository()

    private val _notificationsState = MutableStateFlow(NotificationUserState())
    val notificationsState = _notificationsState.asStateFlow()

    fun loadNotification(){
        viewModelScope.launch {
            val response = notificationRepository.getAllNotifications()
            val dataPage = response.data
            val listData: List<NotificationData> = dataPage.data
            listData.forEach { item ->
                _notificationsState.value = _notificationsState.value.copy(
                    title = item.title,
                    message = item.description
                )
            }

            _
        }
    }



//    init {
//        fetchNotifications()
//    }
//
//    // Lấy dữ liệu từ API
//    private fun fetchNotifications() {
//        viewModelScope.launch {
//            try {
//                val notificationsState = notificationRepository.getAllNotifications()
//                // Phân loại thông báo dựa trên type
//                _userNotifications.value = notificationsState.filter { it.type == "user" }
//                _recruiterNotifications.value = notificationsState.filter { it.type == "recruiter" }
//            } catch (e: Exception) {
//                // Xử lý lỗi đơn giản: đặt danh sách rỗng
//                _userNotifications.value = emptyList()
//                _recruiterNotifications.value = emptyList()
//            }
//        }
//    }

    // Đánh dấu một thông báo là đã đọc
//    fun markAsRead(notification: NotificationData) {
//        _userNotifications.value = _userNotifications.value.map {
//            if (it == notification) it.copy(isRead = true) else it
//        }
//        _recruiterNotifications.value = _recruiterNotifications.value.map {
//            if (it == notification) it.copy(isRead = true) else it
//        }
//    }
//
//    // Đánh dấu tất cả thông báo là đã đọc
//    fun markAllAsRead() {
//        _userNotifications.value = _userNotifications.value.map { it.copy(isRead = true) }
//        _recruiterNotifications.value = _recruiterNotifications.value.map { it.copy(isRead = true) }
//    }
//
//    // Kiểm tra xem có thông báo chưa đọc không
//    fun hasUnreadNotifications(): Boolean {
//        return _userNotifications.value.any { !it.isRead } || _recruiterNotifications.value.any { !it.isRead }
//    }
//
//    // Nhóm thông báo theo ngày
//    fun groupNotificationsByDate(notificationsState: List<NotificationData>): Map<String, List<NotificationData>> {
//        return notificationsState.groupBy { getRelativeDate(it.time) }
//    }

//    fun gotoSender(navController: NavController) {
//        when (_notificationUserStata.value.typeNotification) {
//            "COMPANY" -> navController.navigate("company_screen/${_notificationUserStata.value.senderID}")
//            "ADMIN" -> navController.navigate("profile")
//        }
//
//    }
}