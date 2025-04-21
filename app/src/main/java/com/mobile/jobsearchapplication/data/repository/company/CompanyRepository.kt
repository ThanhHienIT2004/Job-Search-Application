package com.mobile.jobsearchapplication.data.repository.company

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.company.Company
import com.mobile.jobsearchapplication.data.model.company.CompanyDetailResponse
import com.mobile.jobsearchapplication.data.model.job.JobDetailResponse
import com.mobile.jobsearchapplication.data.remote.company.CompanyApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient.companyApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient.jobApiService

class CompanyRepository: CompanyApiService {
    override suspend fun getCompanyDetail(companyId: String): CompanyDetailResponse<Company> {
        return try {
            companyApiService.getCompanyDetail(companyId)
        } catch (e: Exception) {
            CompanyDetailResponse(data = null, message = "Error fetching company detail: ${e.message}")
        }
    }

}