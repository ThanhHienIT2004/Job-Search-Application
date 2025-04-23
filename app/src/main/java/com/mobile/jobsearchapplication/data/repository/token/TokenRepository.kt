package com.mobile.jobsearchapplication.data.repository.token

import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.token.Token
import com.mobile.jobsearchapplication.data.remote.token.TokenApiService

class TokenRepository(private val api: TokenApiService) {

    suspend fun createToken(id: String, token: String): ApiResponse<Token> {
        val params = Token(id, token)
        return api.createToken(params)
    }

    suspend fun getToken(id: String): ApiResponse<Token> {
        return api.getToken(id)
    }
}
