package com.mobile.jobsearchapplication.data.repository.jobCategory

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.jobcategory.JobCategory
import com.mobile.jobsearchapplication.data.remote.jobcategory.JobCategoryApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient
import retrofit2.http.Query

class JobCategoryRepository : JobCategoryApiService {
    private val jobCategoryApi = RetrofitClient.jobCategoryApiService

    override suspend fun getAllJobCategories(
        @Query(value = "page") page: String,
        @Query(value = "limit") limit: String
    ): ApiResponse<JobCategory> {
        return try {
            val response = jobCategoryApi.getAllJobCategories(page, limit)
            ApiResponse(
                message = response.message,
                data = response.data
            )
        } catch (e: Exception) {
            ApiResponse(
                message = e.message.toString(),
                data = null
            )
        }
    }
}