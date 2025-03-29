package com.mobile.jobsearchapplication.ui.features.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseViewModel

class AuthViewModel : BaseViewModel() {
    var isLogin by mutableStateOf(true)
        private set
    var isShowGuide by mutableStateOf(true)

    var textButtonLogin by mutableStateOf("Đăng nhập")
        private set

    var isReverseRow by mutableStateOf(false)
        private set

    // thay đổi trạng thái khi keo nút
    fun onDragLogin(state: Boolean) {
        isLogin = state
        isShowGuide = state
        textButtonLogin = if (state) "Đăng nhập" else "Đăng kí"
        isReverseRow = !state
    }


    // tạo button đăng nhâp khác
    sealed class IConSingUp(val icon: Int, val text: String) {
        data object Google : IConSingUp(R.drawable.ic_google, "Google")
        data object Facebook : IConSingUp(R.drawable.ic_facebook, "Facebook")
    }

    val iConSingUpItems =  listOf(IConSingUp.Google, IConSingUp.Facebook)

}