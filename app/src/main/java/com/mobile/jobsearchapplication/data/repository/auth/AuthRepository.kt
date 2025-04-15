package com.mobile.jobsearchapplication.data.repository.auth

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.remote.auth.AuthApiService

class AuthRepository(
    private val authApiService: AuthApiService
) : AuthApiService {
    override suspend fun createUser(
        uuid: String
    ): BaseResponse<Any> {
        return try {
            BaseResponse(
                isSuccess = true,
                message = "Tạo tài khoản thành công",
                data = authApiService.createUser(uuid).data
            )
        } catch (e: Exception) {
            BaseResponse(
                isSuccess = false,
                message = "Lỗi khi tạo tài khoản: ${e.message}\""
            )
        }
    }
}