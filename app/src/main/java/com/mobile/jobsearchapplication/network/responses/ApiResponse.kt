package com.mobile.jobsearchapplication.network.responses

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