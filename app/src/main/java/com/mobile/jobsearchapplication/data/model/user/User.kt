package com.mobile.jobsearchapplication.data.model.user

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    @SerializedName("password_hash") val passwordHash: String,
    val role: String,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("profile_picture") val profilePicture: String?,
    val gender: String,
    val bio: String?,
    val location: String?,
    @SerializedName("cv_url") val cvUrl: String?,
    val education: String?,
    val experience: String?,
    val skills: String?,
    @SerializedName("created_at") val createdAt: Date
)