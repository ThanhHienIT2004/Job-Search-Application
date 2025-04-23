package com.mobile.jobsearchapplication.data.remote.user

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.user.FavoriteJobPosting
import com.mobile.jobsearchapplication.data.model.user.UpdateInfoUser
import com.mobile.jobsearchapplication.data.model.user.User
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @Multipart
    @PUT("user/updateImage")
    suspend fun updateImage(
        @Query("uuid") uuid: String,
        @Part avatar: MultipartBody.Part,
        @Part cv: MultipartBody.Part? = null
    ): BaseResponse<User>

    @PUT("user/favoriteJobs")
    suspend fun favoriteJobPosting(
        @Query("uuid") uuid: String,
        @Body request: FavoriteJobPosting
    ): BaseResponse<Any>
}