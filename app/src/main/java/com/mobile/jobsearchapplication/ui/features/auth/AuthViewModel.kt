package com.mobile.jobsearchapplication.ui.features.auth

import com.google.firebase.auth.FirebaseAuth
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthState(
    val isLoggedIn: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val isSuccessRegister: Boolean = false
)

data class ButtonAuthState(
    val isLoginScreen: Boolean = true,
    val isFirstLoad: Boolean = true,
    val isDraggingButton: Boolean = false,
    val textButton: String = "Đăng nhập"
)

sealed class IConSingUp(val icon: Int, val text: String) {
    data object Google : IConSingUp(R.drawable.ic_google, "Google")
    data object Facebook : IConSingUp(R.drawable.ic_facebook, "Facebook")
}

open class AuthViewModel : BaseViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private val _buttonState = MutableStateFlow(ButtonAuthState())
    val buttonState = _buttonState.asStateFlow()

    val listIconSignIn =  listOf(IConSingUp.Google, IConSingUp.Facebook)

    private val firebaseAuth = FirebaseAuth.getInstance()

    // thay đổi trạng thái khi kéo nút
    fun onDragButton(state: Boolean) {
        _buttonState.value = _buttonState.value.copy(
            isLoginScreen = state,
            isFirstLoad = false,
            isDraggingButton = !state,
            textButton = if(state) "Đăng nhập" else "Đăng kí"
        )
    }

    fun doCheckUserLoggedIn() {
        if (isUserLoggedIn())
            _authState.value = _authState.value.copy(isLoggedIn = true)
    }

    fun doFirstLoad() {
        _buttonState.value = _buttonState.value.copy(isFirstLoad = false)
    }

    fun onDraggingButton() {
        _buttonState.value = _buttonState.value.copy(isDraggingButton = true)
    }

    fun onSuccessLogin(state: Boolean) {
        _authState.value = _authState.value.copy(isSuccessLogin = state)
    }

    fun onSuccessRegister(state: Boolean) {
        _authState.value = _authState.value.copy(isSuccessRegister = state)
    }
}