package com.mobile.jobsearchapplication.data.remote.jobcategory

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.jobcategory.JobCategory
import retrofit2.http.GET

interface JobCategoryApiService {
    @GET("job-categories")
    suspend fun getAllJobCategories(): ApiResponse<JobCategory>
}