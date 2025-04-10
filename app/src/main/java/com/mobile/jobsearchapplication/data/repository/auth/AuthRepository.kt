package com.mobile.jobsearchapplication.data.repository.auth

import android.util.Log
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.auth.CreateUserRequest
import com.mobile.jobsearchapplication.data.remote.auth.AuthApiService

class AuthRepository(
    private val authApiService: AuthApiService
) : AuthApiService {
    override suspend fun createUser(
        request: CreateUserRequest
    ): BaseResponse<CreateUserRequest> {
        return try {
            val response = authApiService.createUser(request)
            response
        } catch (e: Exception) {
            BaseResponse(
                isSuccess = false,
                message = "Lỗi khi tạo tài khoản: ${e.message}\""
            )
        }
    }
}