package com.mobile.jobsearchapplication.data.remote.auth

import com.mobile.jobsearchapplication.data.model.BaseResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @POST("auth/createUser")
    suspend fun createUser(
        @Query("uuid") uuid: String,
    ): BaseResponse<Any>
}