package com.mobile.jobsearchapplication.data.repository.auth

interface AuthService {
    suspend fun singUp(fullName: String, email: String, password: String)
}