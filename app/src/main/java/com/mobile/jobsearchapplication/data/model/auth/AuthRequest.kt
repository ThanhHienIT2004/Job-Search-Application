package com.mobile.jobsearchapplication.data.model.auth

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)
