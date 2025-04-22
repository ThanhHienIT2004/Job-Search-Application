package com.mobile.jobsearchapplication.data.remote.jobApplication

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.jobapplication.AppliedUserWithApplication
import com.mobile.jobsearchapplication.data.model.jobapplication.JobApplication
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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

    @GET("applications/getAppliedUsersByJobId")
    suspend fun getAppliedUsersByJobId(
        @Query("jobId") jobId: String
    ): BaseResponse<List<AppliedUserWithApplication>>
}



data class JobApplicationResponse(
    val message: String,
    val data: JobApplication? = null
)
data class ApiResponse<T>(
    val data: ResponseData<T>?,
    val message: String
)

data class ResponseData<T>(
    val pageCount: Int,
    val data: List<T>
)