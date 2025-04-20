package com.mobile.jobsearchapplication.utils

import com.mobile.jobsearchapplication.data.remote.application.JobApplicationService
import com.mobile.jobsearchapplication.data.remote.auth.AuthApiService
import com.mobile.jobsearchapplication.data.remote.job.JobApiService
import com.mobile.jobsearchapplication.data.remote.jobcategory.JobCategoryApiService
import com.mobile.jobsearchapplication.data.remote.notification.NotificationApiService
import com.mobile.jobsearchapplication.data.remote.user.UserApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL =
//        "http://192.168.1.35:8080/"
//        "http://192.168.112.102:8080/"
//        "http://192.168.2.193:8080/"
        "http:// 192.168.112.103:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jobApiService: JobApiService by lazy {
        retrofit.create(JobApiService::class.java)
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
    val jobApplicationApiService: JobApplicationService by lazy {
        retrofit.create(JobApplicationService::class.java)
    }

    val notificationApiService : NotificationApiService by lazy {
        retrofit.create(NotificationApiService::class.java)
    }
}