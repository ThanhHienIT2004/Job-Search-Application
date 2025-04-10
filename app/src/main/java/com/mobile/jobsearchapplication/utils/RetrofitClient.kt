package com.mobile.jobsearchapplication.utils

import com.mobile.jobsearchapplication.data.remote.auth.AuthApiService
import com.mobile.jobsearchapplication.data.remote.job.JobApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://127.0.0.1:8080/"

//    http://127.0.0.1:8080
//    http://192.168.2.193:8080

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jobApiService: JobApiService by lazy {
        retrofit.create(JobApiService::class.java)
    }

    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}