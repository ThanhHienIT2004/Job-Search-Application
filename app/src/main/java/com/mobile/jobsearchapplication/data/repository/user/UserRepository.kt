package com.mobile.jobsearchapplication.data.repository.user

import android.util.Log
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.user.UpdateInfoUser
import com.mobile.jobsearchapplication.data.model.user.User
import com.mobile.jobsearchapplication.data.remote.user.UserApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient
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
            Log.e("getInfo", "Lỗi: ${e.message}", e)
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
            Log.e("updateInfo", "Lỗi: ${e.message}", e)
            BaseResponse(
                isSuccess = false,
                message = "Lỗi khi cập nhật thông tin: ${e.message}",
                data = null
            )
        }
    }
}