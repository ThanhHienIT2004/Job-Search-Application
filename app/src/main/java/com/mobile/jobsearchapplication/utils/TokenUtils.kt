package com.mobile.jobsearchapplication.utils

import android.content.Context
import androidx.core.content.edit
import com.google.firebase.messaging.FirebaseMessaging
import com.mobile.jobsearchapplication.data.repository.token.TokenRepository
import kotlinx.coroutines.tasks.await
import java.util.UUID

object TokenUtils {
    private const val PREFS_NAME = "com.mobile.jobsearchapplication.prefs"
    private const val KEY_FCM_TOKEN = "fcm_token"

    /**
     * Lấy hoặc làm mới FCM token và lưu trữ cục bộ.
     * @param context Context để truy cập SharedPreferences
     * @param forceRefresh Buộc làm mới token nếu true
     * @return Token nếu thành công, null nếu thất bại
     */
    suspend fun getOrRefreshToken(context: Context, forceRefresh: Boolean = false): String? {
        return try {
            // Kiểm tra token đã lưu
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val cachedToken = prefs.getString(KEY_FCM_TOKEN, null)

            if (!forceRefresh && !cachedToken.isNullOrBlank()) {
                println("Sử dụng token đã lưu: $cachedToken")
                return cachedToken
            }

            // Lấy token mới từ Firebase
            val newToken = FirebaseMessaging.getInstance().token.await()
            println("Token mới: $newToken")

            // Lưu token vào SharedPreferences
            prefs.edit() { putString(KEY_FCM_TOKEN, newToken) }
            newToken
        } catch (e: Exception) {
            println("Lỗi khi lấy token: ${e.message}")
            null
        }
    }

    /**
     * Gửi token lên server thông qua TokenRepository.
     * @param context Context để truy cập SharedPreferences
     * @param userId ID của người dùng
     * @param tokenRepository Repository để gọi API gửi token
     * @param forceRefresh Buộc làm mới token nếu true
     * @return True nếu gửi thành công, False nếu thất bại
     */
    suspend fun sendTokenToServer(
        context: Context,
        userId: String,
        tokenRepository: TokenRepository,
        forceRefresh: Boolean = false
    ): Boolean {
        if (userId.isBlank()) {
            println("Lỗi: userId trống")
            return false
        }

        // Lấy token
        val token = getOrRefreshToken(context, forceRefresh)
        if (token.isNullOrBlank()) {
            println("Lỗi: Không thể lấy token")
            return false
        }

        return try {
            // Gửi token lên server
            val response = tokenRepository.createToken(userId, token)
            if (response.data != null) {
                println("Gửi token thành công: $token")
                true
            } else {
                println("Gửi token thất bại: ${response.message}")
                false
            }
        } catch (e: Exception) {
            println("Lỗi khi gửi token: ${e.message}")
            false
        }
    }

    /**
     * Xóa token đã lưu cục bộ (dùng khi đăng xuất).
     * @param context Context để truy cập SharedPreferences
     */
    fun clearToken(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_FCM_TOKEN).apply()
        println("Đã xóa token cục bộ")
    }

    /**
     * Tạo token ngẫu nhiên nếu cần (dự phòng).
     * @return Token ngẫu nhiên
     */
    fun generateRandomToken(): String {
        return UUID.randomUUID().toString()
    }
}