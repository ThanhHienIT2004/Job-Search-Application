package com.mobile.jobsearchapplication.data.model.notification

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime
import com.mobile.jobsearchapplication.ui.features.notification.SingleNotification

data class Notification(
    @SerializedName("userId") val userId: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val message: String? = null,
    @SerializedName("type") val typeNotification: String? = null,
    @SerializedName("imageRes") val avatar: Int? = null,
    @SerializedName("createAt") val createAt: String? = null,
    @SerializedName("senderId") val senderID: String? = null,
    @SerializedName("senderName") val senderName: String? = null
) {
    fun toSingleNotification() = SingleNotification(
        avatar = avatar ?: 0,
        title = title ?: "",
        message = message ?: "",
        createAt = createAt,
        senderID = senderID ?: "",
        senderName = senderName ?: "Unknown",
        typeNotification = typeNotification ?: ""
    )
}

data class NotificationResponse<T>(
    val data: T,
    val message: String
)