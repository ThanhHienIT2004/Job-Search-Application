package com.mobile.jobsearchapplication.utils

import android.annotation.SuppressLint
import android.content.Context

class SharedPreferencesUtils {
    companion object {
        @SuppressLint("CommitPrefEdits")
        fun saveUserLoggedInState(context: Context, isLoggedIn: Boolean, userId: String?) {
            val sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putBoolean("isLoggedIn", isLoggedIn)
            editor.putString("userId", userId)
            editor.apply()
        }

        fun isUserLoggedIn(context: Context): Boolean {
            val sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("isLoggedIn", false)
        }

        fun getLoggedInUserId(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            return sharedPreferences.getString("userId", null)
        }
    }
}