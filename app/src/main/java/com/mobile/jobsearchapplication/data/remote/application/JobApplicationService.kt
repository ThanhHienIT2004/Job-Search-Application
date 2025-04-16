package com.mobile.jobsearchapplication.data.remote.application

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.jobapplication.JobApplication
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JobApplicationService {
    @POST("applications/apply")
    suspend fun submitApplication(@Body jobApplication: JobApplication): ApiResponse<JobApplication>
}