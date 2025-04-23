package com.mobile.jobsearchapplication.data.repository.user

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.user.FavoriteJobPosting
import com.mobile.jobsearchapplication.data.model.user.UpdateInfoUser
import com.mobile.jobsearchapplication.data.model.user.User
import com.mobile.jobsearchapplication.data.remote.user.UserApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Query

class UserRepository: UserApiService {
    private val userApiService = RetrofitClient.userApiService

    override suspend fun getInfo(uuid: String): BaseResponse<User> {
        return try {
            val response = userApiService.getInfo(uuid)
            BaseResponse(
                isSuccess = true,
                message = response.message,
                data = response.data
            )
        } catch (e: Exception) {
            BaseResponse(
                isSuccess = false,
                message = "Lỗi khi lấy thông tin: ${e.message}",
                data = null
            )
        }
    }

    override suspend fun updateInfo(
        @Query(value = "uuid") uuid: String,
        @Body request: UpdateInfoUser
    ): BaseResponse<User> {
        return try {
            val response = userApiService.updateInfo(uuid, request)
            BaseResponse(
                isSuccess = true,
                message = response.message,
                data = response.data
            )
        } catch (e: Exception) {
            BaseResponse(
                isSuccess = false,
                message = "Lỗi khi cập nhật thông tin: ${e.message}",
                data = null
            )
        }
    }

    override suspend fun updateImage(
        uuid: String,
        avatar: MultipartBody.Part,
        cv: MultipartBody.Part?
    ): BaseResponse<User> {
        return try {
            userApiService.updateImage(uuid, avatar, cv)
        } catch (e: Exception) {
            BaseResponse(
                isSuccess = false,
                message = "Lỗi khi cập nhật thông tin: ${e.message}",
                data = null
            )
        }
    }

    override suspend fun favoriteJobPosting(
        uuid: String,
        request: FavoriteJobPosting
    ): BaseResponse<Any> {

        return try {
            val response = userApiService.favoriteJobPosting(uuid, request)
            BaseResponse(
                isSuccess = true,
                message = response.message,
                data = response.data
            )
        } catch (e: Exception) {
            BaseResponse(
                isSuccess = false,
                message = "Lỗi khi cập nhật thông tin: ${e.message}",
                data = null
            )
        }
    }
}