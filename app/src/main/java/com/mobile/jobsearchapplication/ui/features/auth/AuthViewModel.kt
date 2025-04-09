package com.mobile.jobsearchapplication.ui.features.auth

import com.google.firebase.auth.FirebaseUser
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthState(
    val isSuccessLogin: Boolean = false,
    val isStatusButton: Boolean = true,
    val isFirstLoad: Boolean = true,
    val isDraggingButton: Boolean = false,
    val textButton: String = "Đăng nhập"
)

class AuthViewModel : BaseViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user = _user.asStateFlow()

    // thay đổi trạng thái khi keo nút
    fun onDragButton(state: Boolean) {
        _authState.value = _authState.value.copy(
            isStatusButton = state,
            isFirstLoad = false,
            isDraggingButton = !state,
            textButton = if(state) "Đăng nhập" else "Đăng kí"
        )
    }

    fun onFirstLoad() {
        _authState.value = _authState.value.copy(isFirstLoad = false)
    }

    fun onDragging() {
        _authState.value = _authState.value.copy(isDraggingButton = true)
    }

    fun onSuccessLogin() {
        _authState.value = _authState.value.copy(isSuccessLogin = true)
    }

    // tạo button đăng nhâp khác
    sealed class IConSingUp(val icon: Int, val text: String) {
        data object Google : IConSingUp(R.drawable.ic_google, "Google")
        data object Facebook : IConSingUp(R.drawable.ic_facebook, "Facebook")
    }

    val iConSingUpItems =  listOf(IConSingUp.Google, IConSingUp.Facebook)
}