package com.mobile.jobsearchapplication.data.model

import android.provider.ContactsContract.CommonDataKinds.Email

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)
