package com.mobile.jobsearchapplication.data.repository.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor() : AuthService {
    override suspend fun singUp(fullName: String, email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

}