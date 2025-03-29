package com.mobile.jobsearchapplication.data.retrofit

import com.mobile.jobsearchapplication.data.api.IAuthApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "127.0.0.1:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: IAuthApi by lazy { retrofit.create(IAuthApi::class.java) }
}