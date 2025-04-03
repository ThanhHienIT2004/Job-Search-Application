package com.mobile.jobsearchapplication.data.api.auth

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.User
import retrofit2.http.POST

interface AuthApi {
    @POST
    suspend fun Register(): BaseResponse<User>
}