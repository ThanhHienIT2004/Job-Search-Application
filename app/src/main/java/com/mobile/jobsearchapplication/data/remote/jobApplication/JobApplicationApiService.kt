package com.mobile.jobsearchapplication.data.remote.jobApplication

import com.mobile.jobsearchapplication.data.model.jobapplication.JobApplication
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface JobApplicationApiService {
    @Multipart
    @POST("/applications/apply")
    suspend fun submitApplication(
        @Part("jobId") jobId: RequestBody,
        @Part("userId") userId: RequestBody,
        @Part("status") status: RequestBody,
        @Part("coverLetter") coverLetter: RequestBody,
        @Part("additionalInfo") additionalInfo: RequestBody,
        @Part cv: MultipartBody.Part
    ): Response<JobApplicationResponse>
}

data class JobApplicationResponse(
    val message: String,
    val data: JobApplication? = null
)