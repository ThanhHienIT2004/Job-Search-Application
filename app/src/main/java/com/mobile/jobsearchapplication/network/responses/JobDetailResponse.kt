package com.mobile.jobsearchapplication.network.responses

import com.mobile.jobsearchapplication.data.model.Company
import com.mobile.jobsearchapplication.data.model.Job
import com.mobile.jobsearchapplication.data.model.JobCategory


data class JobDetailResponse(
    val job: Job,
    val company: Company,
    val categories: List<JobCategory>
)