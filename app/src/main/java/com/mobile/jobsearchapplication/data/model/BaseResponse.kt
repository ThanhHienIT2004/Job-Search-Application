package com.mobile.jobsearchapplication.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T? = null
)


// Đại diện cho object "data" lồng nhau trong JSON
data class PaginatedData<T>(
    val pageCount: Int,
    val data: List<T>
)

// Cập nhật ApiResponse để khớp với API thực tế
data class ApiResponse<T>(
    val data: PaginatedData<T>?,
    val message: String
)