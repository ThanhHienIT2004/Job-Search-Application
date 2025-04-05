package com.mobile.jobsearchapplication.data.api.user

import retrofit2.http.GET

interface UserApi {
    @GET("user/get-user")
    suspend fun getUser()
}