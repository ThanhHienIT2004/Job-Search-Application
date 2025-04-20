package com.mobile.jobsearchapplication.data.repository.job

import com.google.gson.JsonObject
import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.company.Company
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.model.job.JobByCategory
import com.mobile.jobsearchapplication.data.model.job.JobDetailResponse
import com.mobile.jobsearchapplication.data.model.user.User
import com.mobile.jobsearchapplication.data.remote.job.JobApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient.jobApiService
import java.io.IOException
import java.util.UUID

class JobRepository : JobApiService {
    override suspend fun getJobs(): ApiResponse<Job> {
        TODO("Not yet implemented")
    }

    override suspend fun getJobDetail(jobId: String): JobDetailResponse<Job> {
        return try {
            jobApiService.getJobDetail(jobId)
        } catch (e: Exception) {
            JobDetailResponse(data = null, message = "Error fetching job detail: ${e.message}")
        }
    }

    override suspend fun getJobsByCategory(categoryId: String): JobByCategory {
        return try {
            jobApiService.getJobsByCategory(categoryId)
        } catch (e: Exception) {
            JobByCategory(data = emptyList(), message = "Error fetching jobs: ${e.message}")
        }
    }

    override suspend fun getFavoriteJobs(userId: String): BaseResponse<List<Job>> {
        return try {
            jobApiService.getFavoriteJobs(userId)
        } catch (e: Exception) {
            BaseResponse(isSuccess = false ,data = null, message = "Error fetching posted jobs: ${e.message}")
        }
    }

    override suspend fun getPostedJobs(userId: String): BaseResponse<List<Job>> {
        return try {
            jobApiService.getPostedJobs(userId)
        } catch (e: Exception) {
            BaseResponse(isSuccess = false ,data = null, message = "Error fetching posted jobs: ${e.message}")
        }
    }

    override suspend fun getAppliedJobs(userId: String): BaseResponse<List<Job>> {
        return try {
            jobApiService.getAppliedJobs(userId)
        } catch (e: Exception) {
            BaseResponse(isSuccess = false ,data = null, message = "Error fetching posted jobs: ${e.message}")
        }
    }

    override suspend fun getUser(userId: UUID): ApiResponse<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getCompany(companyId: UUID): ApiResponse<Company> {
        TODO("Not yet implemented")
    }

    override suspend fun createJob(job: JsonObject): ApiResponse<Job> {
        // Log JSON để debug
        println("Sending JSON: $job")
        // Gọi API
        return jobApiService.createJob(job)
    }
}