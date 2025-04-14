package com.mobile.jobsearchapplication.data.remote.user

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.user.UpdateInfoUser
import com.mobile.jobsearchapplication.data.model.user.User
import com.mobile.jobsearchapplication.ui.features.profile.InfoProfileState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApiService {

    @GET("user/getInfo")
    suspend fun getInfo(
        @Query("uuid") uuid: String
    ): BaseResponse<User>

    @PUT("user/updateInfo")
    suspend fun updateInfo(
        @Query("uuid") uuid: String,
        @Body request: UpdateInfoUser
    ): BaseResponse<User>


}