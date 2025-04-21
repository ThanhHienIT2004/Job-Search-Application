package com.mobile.jobsearchapplication.data.remote.job

import com.google.gson.JsonObject
import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.company.Company
import com.mobile.jobsearchapplication.data.model.job.AppliedJobs
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.model.job.JobByCategory
import com.mobile.jobsearchapplication.data.model.job.JobDetailResponse
import com.mobile.jobsearchapplication.data.model.notification.Notification
import com.mobile.jobsearchapplication.data.model.user.User
import org.checkerframework.checker.units.qual.A
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface JobApiService {
    @GET("jobs")
    suspend fun getJobs(): ApiResponse<Job>

    @GET("jobs/getById/{id}")
    suspend fun getJobDetail(@Path("id") jobId: String): JobDetailResponse<Job>

    @GET("jobs/getJobsByCategory")
    suspend fun getJobsByCategory(
        @Query("categoryId") categoryId: String
    ): JobByCategory

    @GET("jobs/getFavoriteJobs")
    suspend fun getFavoriteJobs(
        @Query("userId") userId: String
    ): BaseResponse<List<Job>>

    @GET("jobs/getPostedJobs")
    suspend fun getPostedJobs(
        @Query("userId") userId: String
    ): BaseResponse<List<Job>>

    @GET("jobs/getAppliedJobs")
    suspend fun getAppliedJobs(
        @Query("userId") userId: String
    ): BaseResponse<List<AppliedJobs>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: UUID): ApiResponse<User>

    @GET("companies/{id}")
    suspend fun getCompany(@Path("id") companyId: UUID): ApiResponse<Company>

    @POST("jobs/add")
    suspend fun createJob(@Body job: JsonObject): ApiResponse<Job>



//    @GET("users/{userId}/saved-jobs")
//    suspend fun getSavedJobs(@Path("userId") userId: UUID): ApiResponse<List<SavedJob>>
}