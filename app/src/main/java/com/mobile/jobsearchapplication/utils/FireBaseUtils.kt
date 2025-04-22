package com.mobile.jobsearchapplication.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class FireBaseUtils {

    companion object {
        val auth = FirebaseAuth.getInstance()
        val token = FirebaseMessaging.getInstance().token

        fun isUserLoggedIn(): Boolean {
            return auth.currentUser != null
        }

        fun getLoggedInUserId(): String {
            return auth.currentUser?.uid.toString()
        }

        fun getCurrentUserEmail(): String {
            return auth.currentUser?.email.toString()
        }

        fun getCurrentUserName(): String {
            return auth.currentUser?.displayName.toString()
        }

        fun getDeviceToken(): String {
            return token.toString()
        }
    }

}