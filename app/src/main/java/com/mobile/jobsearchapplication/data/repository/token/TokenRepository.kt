package com.mobile.jobsearchapplication.data.repository.token

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.BaseResponse
import com.mobile.jobsearchapplication.data.model.token.Token
import com.mobile.jobsearchapplication.data.remote.token.TokenApiService
import com.mobile.jobsearchapplication.utils.RetrofitClient

class TokenRepository : TokenApiService {
    private val api = RetrofitClient.TokenApiService

    override suspend fun getToken(id: String): ApiResponse<Token> {
        return api.getToken(id)
    }

    override suspend fun createToken(
        id: String,
        token: String
    ): ApiResponse<Token> {
        return api.createToken(id, token)
    }
}
