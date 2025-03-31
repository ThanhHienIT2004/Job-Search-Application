package com.mobile.jobsearchapplication.data.service.auth

interface AuthService {
    suspend fun singUp(fullName: String, email: String, password: String)
}