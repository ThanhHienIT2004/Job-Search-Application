package com.mobile.jobsearchapplication.data.remote.token

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.token.Token
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApiService {
    @GET("token")
    suspend fun getToken(
        @Query("id") id: String
    ): ApiResponse<Token>

    @POST("token/createToken")
    suspend fun createToken(
        @Query("id") id: String,
        @Query("token") token: String
    ): ApiResponse<Token>
}