package com.mobile.jobsearchapplication.network

import com.mobile.jobsearchapplication.data.model.Company
import com.mobile.jobsearchapplication.data.model.Job
import com.mobile.jobsearchapplication.data.model.JobDetailResponse
import com.mobile.jobsearchapplication.data.model.User
import com.mobile.jobsearchapplication.network.responses.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface JobApiService {
    @GET("jobs") // Endpoint tương ứng với http://127.0.0.1:8080/jobs
    suspend fun getJobs(): ApiResponse<Job>
    // Các phương thức khác giữ nguyên nếu cần
    @GET("jobs/{id}")
    suspend fun getJobDetail(@Path("id") jobId: String): JobDetailResponse<Job>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: UUID): ApiResponse<User>

    @GET("companies/{id}")
    suspend fun getCompany(@Path("id") companyId: UUID): ApiResponse<Company>

    @POST("jobs")
    suspend fun createJob(@Body job: Job): ApiResponse<Job>

//    @GET("users/{userId}/saved-jobs")
//    suspend fun getSavedJobs(@Path("userId") userId: UUID): ApiResponse<List<SavedJob>>
}