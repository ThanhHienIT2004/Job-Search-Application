package com.mobile.jobsearchapplication.data.repository.job

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.company.Company
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.model.job.JobByCategory
import com.mobile.jobsearchapplication.data.model.job.JobDetailResponse
import com.mobile.jobsearchapplication.data.model.user.User
import com.mobile.jobsearchapplication.data.remote.job.JobApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient.jobApiService
import java.util.UUID

class JobRepository : JobApiService {
    override suspend fun getJobs(): ApiResponse<Job> {
        TODO("Not yet implemented")
    }

    override suspend fun getJobDetail(jobId: String): JobDetailResponse<Job> {
        TODO("Not yet implemented")
    }

    override suspend fun getJobsByCategory(categoryId: String): JobByCategory {
        return try {
            val response = jobApiService.getJobsByCategory(categoryId)
            response
        } catch (e: Exception) {
            JobByCategory(data = emptyList(), message = "Error fetching jobs: ${e.message}")
        }
    }

    override suspend fun getUser(userId: UUID): ApiResponse<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getCompany(companyId: UUID): ApiResponse<Company> {
        TODO("Not yet implemented")
    }

    override suspend fun createJob(job: Job): ApiResponse<Job> {
        TODO("Not yet implemented")
    }
}