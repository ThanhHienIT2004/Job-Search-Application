package com.mobile.jobsearchapplication.data.remote.jobcategory

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.jobcategory.JobCategory
import retrofit2.http.GET
import retrofit2.http.Query

    interface JobCategoryApiService {
        @GET("jobCategories/getAll")
        suspend fun getAllJobCategories(
            @Query("page") page: String,
            @Query("limit") limit: String
        ): ApiResponse<JobCategory>
    }