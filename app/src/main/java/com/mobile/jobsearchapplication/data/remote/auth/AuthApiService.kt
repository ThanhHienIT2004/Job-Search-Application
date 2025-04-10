package com.mobile.jobsearchapplication.data.remote.auth

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.auth.CreateUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/createUser")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): BaseResponse<CreateUserRequest>
}