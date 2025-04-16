package com.mobile.jobsearchapplication.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T? = null
)


// Đại diện cho object "data" lồng nhau trong JSON
data class PaginatedData<T>(
    @SerializedName("pageCount") val pageCount: Int,
    @SerializedName("data")  val data: List<T>
) 

// Cập nhật ApiResponse để khớp với API thực tế
data class ApiResponse<T>(
    @SerializedName("data") val data: PaginatedData<T>?,
    @SerializedName("message") val message: String
)

data class NotificationListResponse<T>(
    val data: List<T>?,
    val message: String
)