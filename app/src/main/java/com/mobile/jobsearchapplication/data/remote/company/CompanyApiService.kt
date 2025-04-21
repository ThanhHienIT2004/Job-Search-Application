package com.mobile.jobsearchapplication.data.remote.company

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.company.Company
import com.mobile.jobsearchapplication.data.model.company.CompanyDetailResponse
import com.mobile.jobsearchapplication.data.model.user.User
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface CompanyApiService {
    @GET("company/get/{id}")
    suspend fun getCompanyDetail(@Path("id") companyId: String): CompanyDetailResponse<Company>
}