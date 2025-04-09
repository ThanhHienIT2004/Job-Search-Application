package com.mobile.jobsearchapplication.ui.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getCurrentUserEmail
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserState(
    val isLoggedIn: Boolean = isUserLoggedIn(),
    val email: String = if(isLoggedIn) getCurrentUserEmail() else "Đăng nhập / Đăng ký"
)

class UserViewModel : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val  userState = _userState.asStateFlow()

    fun singOut() {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signOut()
        }
    }
}


