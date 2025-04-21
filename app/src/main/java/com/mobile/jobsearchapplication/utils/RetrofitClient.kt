package com.mobile.jobsearchapplication.utils

import com.mobile.jobsearchapplication.data.remote.auth.AuthApiService
import com.mobile.jobsearchapplication.data.remote.company.CompanyApiService
import com.mobile.jobsearchapplication.data.remote.job.JobApiService
import com.mobile.jobsearchapplication.data.remote.jobApplication.JobApplicationApiService
import com.mobile.jobsearchapplication.data.remote.jobcategory.JobCategoryApiService
import com.mobile.jobsearchapplication.data.remote.user.UserApiService
import retrofit2.Retrofit

import com.mobile.jobsearchapplication.data.remote.notification.NotificationApiService
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL =
        "http://192.168.112.101:8080/"
//        "http://192.168.2.217:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jobApiService: JobApiService by lazy {
        retrofit.create(JobApiService::class.java)
    }
    val companyApiService: CompanyApiService by lazy {
        retrofit.create(CompanyApiService::class.java)
    }

    val jobCategoryApiService: JobCategoryApiService by lazy {
        retrofit.create(JobCategoryApiService::class.java)
    }

    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val userApiService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
    val jobApplicationApiService: JobApplicationApiService by lazy {
        retrofit.create(JobApplicationApiService::class.java)
    }

    val notificationApiService : NotificationApiService by lazy {
        retrofit.create(NotificationApiService::class.java)
    }
}