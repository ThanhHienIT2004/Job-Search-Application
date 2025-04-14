package com.mobile.jobsearchapplication.utils

import com.google.firebase.auth.FirebaseAuth

class FireBaseUtils {

    companion object {
        val auth = FirebaseAuth.getInstance()

        fun isUserLoggedIn(): Boolean {
            return auth.currentUser != null
        }

        fun getLoggedInUserId(): String {
            return auth.currentUser?.uid.toString()
        }

        fun getCurrentUserEmail(): String {
            return auth.currentUser?.email.toString()
        }
    }

}