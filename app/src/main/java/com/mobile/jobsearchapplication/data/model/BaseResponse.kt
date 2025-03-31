package com.mobile.jobsearchapplication.data.model

data class BaseResponse<T> (
    val isSuccess: Boolean,
    val message: String,
    val data: List<T>? = null
)
