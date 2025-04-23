package com.mobile.jobsearchapplication.data.repository.jobApplication

import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.jobapplication.AppliedUserWithApplication
import com.mobile.jobsearchapplication.data.remote.jobApplication.JobApplicationApiService
import com.mobile.jobsearchapplication.data.remote.jobApplication.JobApplicationResponse
import com.mobile.jobsearchapplication.utils.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class JobApplicationRepository : JobApplicationApiService {
    private val api = RetrofitClient.jobApplicationApiService

    override suspend fun submitApplication(
        jobId: RequestBody,
        userId: RequestBody,
        status: RequestBody,
        coverLetter: RequestBody,
        additionalInfo: RequestBody,
        cv: MultipartBody.Part
    ): Response<JobApplicationResponse> {
        return api.submitApplication(
            jobId = jobId,
            userId = userId,
            status = status,
            coverLetter = coverLetter,
            additionalInfo = additionalInfo,
            cv = cv
        )
    }

    override suspend fun getAppliedUsersByJobId(jobId: String): BaseResponse<List<AppliedUserWithApplication>> {
        return api.getAppliedUsersByJobId(jobId)
    }
}