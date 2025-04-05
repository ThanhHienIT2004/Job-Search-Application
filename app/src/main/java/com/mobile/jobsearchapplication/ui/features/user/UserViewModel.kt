package com.mobile.jobsearchapplication.ui.features.user

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getCurrentUserEmail
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserState(
    val isLoggedIn: Boolean = isUserLoggedIn(),
    val email: String = getCurrentUserEmail(),
)

class UserViewModel : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val  userState = _userState.asStateFlow()

    fun onLoggedIn() {
        _userState.value = _userState.value.copy(isLoggedIn = isUserLoggedIn())
    }

    fun onLogOut() {
        _userState.value = _userState.value.copy(isLoggedIn = false)
    }

    fun singOut() {
        _userState.value = _userState.value.copy(isLoggedIn = false)
        FirebaseAuth.getInstance().signOut()
    }
}


