package com.mobile.jobsearchapplication.data.remote.application

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.jobapplication.JobApplication
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface JobApplicationService {
    @Multipart
    @POST("applications/apply")
    suspend fun submitApplication(@Body jobApplication: JobApplication, @Part cvFile: File?): ApiResponse<JobApplication>
}