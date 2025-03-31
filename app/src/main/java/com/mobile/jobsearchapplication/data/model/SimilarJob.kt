package com.mobile.jobsearchapplication.data.model

data class SimilarJob(
    val id: Int,
    val title: String,
    val company: String,
    val wage: String,
    val postedTime: String,
    val location: String,
    val isPartner: Boolean = false // Nếu là "ĐỐI TÁC", ta có thể hiển thị badge
)
