package com.mobile.jobsearchapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.112.113:8080/" // Địa chỉ ip version 4 của cùng 1 wifi

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jobApiService: JobApiService by lazy {
        retrofit.create(JobApiService::class.java)
    }
}