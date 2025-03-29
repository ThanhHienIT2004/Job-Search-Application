package com.mobile.jobsearchapplication.data.api

import com.mobile.jobsearchapplication.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {
    @POST("auth/registerUser/{AuthRequest}")
    suspend fun registerUser(@Body request: RegisterRequest ): Pair<Boolean, String>


}