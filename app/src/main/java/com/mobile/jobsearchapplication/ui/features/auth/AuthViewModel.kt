package com.mobile.jobsearchapplication.ui.features.auth

import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.repository.auth.AuthRepository
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn
import com.mobile.jobsearchapplication.utils.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
    private val authRepository = AuthRepository(RetrofitClient.authApiService)

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private val _buttonState = MutableStateFlow(ButtonAuthState())
    val buttonState = _buttonState.asStateFlow()

    val listIconSignIn =  listOf(IConSingUp.Google, IConSingUp.Facebook)

    fun resetStateLogged() {
        _authState.value = _authState.value.copy(
            isLoggedIn = false,
            isSuccessLogin = false,
            isSuccessRegister = false
        )
    }

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

    fun signInOther() {
        viewModelScope.launch {
            try {
                showLoading()

                val response = authRepository.createUser(getLoggedInUserId())
                if (response.isSuccess) {
                    _authState.value = _authState.value.copy(isLoggedIn = true)
                }

                hideLoading()

            } catch (e: Exception) {
                showErrorMessage(e.message ?: "Đã xảy ra lỗi khi đăng nhập")
            } finally {
                hideLoading()
            }
        }
    }
}