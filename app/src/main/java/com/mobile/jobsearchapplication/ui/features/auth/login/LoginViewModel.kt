package com.mobile.jobsearchapplication.ui.features.auth.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel() : BaseViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set


    fun onEmailChanged(newEmail: String) {
        email = newEmail
    }
    fun onPasswordChanged(newPassword: String) {
        password = newPassword
    }

    sealed class TextFieldLogin(
        open val value: String,
        open val onClick: (String) -> Unit,
        open val label: String,
        open val isError: Boolean,
        open val errorMessage: String = ""
    ) {
        data class Email(
            override val value: String,
            override val onClick: (String) -> Unit,
            override val label: String = "Email",
            override val isError: Boolean = false,
            override val errorMessage: String = "Email không hợp lệ"
        ) : TextFieldLogin(value, onClick, label, isError, errorMessage)

        data class Password(
            override val value: String,
            override val onClick: (String) -> Unit,
            override val label: String = "Password",
            override val isError: Boolean = false,
            override val errorMessage: String = "Mật khẩu phải trên 6 kí tự"
        ) : TextFieldLogin(value, onClick, label, isError, errorMessage)
    }

    val textFieldLoginItems = listOf(
        TextFieldLogin.Email(email, ::onEmailChanged),
        TextFieldLogin.Password(password, ::onPasswordChanged)
    )

    fun login() {
        viewModelScope.launch {
            try {
                showLoading()
                Firebase.auth.signInWithEmailAndPassword(email, password)
            } catch (e: Exception) {
                showErrorMessage(e.message ?: "Đã xảy ra lỗi khi đăng nhập")
            }
        }
    }
}