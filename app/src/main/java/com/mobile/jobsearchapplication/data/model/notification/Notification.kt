package com.mobile.jobsearchapplication.data.model.notification

import com.google.gson.annotations.SerializedName
import com.mobile.jobsearchapplication.ui.features.notification.SingleNotification

data class Notification(
    @SerializedName("Id") val Id: Long? = null,
    @SerializedName("userId") val userId: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("imageRes") val avatar: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("senderId") val senderID: String? = null,
    @SerializedName("senderName") val senderName: String? = null,
    @SerializedName("isRead") val isRead: Boolean? = false,
    @SerializedName("jobId") val relatedId: String? = null
) {
    fun toSingleNotification() = SingleNotification(
        id = Id ?: "",
        avatar = avatar ?: "",
        title = title ?: "",
        description = description ?: "",
        createdAt = createdAt,
        senderID = senderID ?: "",
        userId = userId ?: "",
        senderName = senderName ?: "Unknown",
        type = type ?: "",
        isRead = isRead == false,
        relateId = relatedId ?: ""
    )
}

data class NotificationResponse<T>(
    val data: T,
    val message: String
)