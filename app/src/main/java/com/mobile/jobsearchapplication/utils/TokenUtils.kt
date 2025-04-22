package com.mobile.jobsearchapplication.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.mobile.jobsearchapplication.data.model.token.Token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

object TokenUtils {
    fun fetchFcmToken(onTokenReady: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    onTokenReady(token)
                } else {
                    onTokenReady(null)
                }
            }
    }
    fun fetchAndSendToken(userId: String) {
        fetchFcmToken { token ->
            if (token != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Tạo đối tượng DeviceTokenParams
                        val params = Token(id = userId, token = token)

                        // Gọi API để gửi token về server
                        val api = RetrofitClient.tokenApiService
                        val response = api.createToken(params)

                    } catch (e: Exception) {
                        Timber.tag("FCM").e("Lỗi gửi token: ${e.message}")
                    }
                }
            }
        }
    }


}
