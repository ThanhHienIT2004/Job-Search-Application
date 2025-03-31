package com.mobile.jobsearchapplication.ui.features.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mobile.jobsearchapplication.data.service.auth.AuthServiceImpl
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel(
//    private val _repos: AuthServiceImpl
) : BaseViewModel() {
    var fullName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
       private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set
    var isCheckedClause by mutableStateOf(false)
        private set

    fun onFullNameChanged(newFullName: String) {
        fullName = newFullName
    }
    fun onEmailChanged(newEmail: String) {
        email = newEmail
    }
    fun onPasswordChanged(newPassword: String) {
        password = newPassword
    }
    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
    }
    fun onCheckedClause() {
        isCheckedClause = true
    }

    fun register() {
        if (!isValidEmail(email)) {
            showErrorMessage("Email không hợp lệ")
            return
        }

        if (password.length < 6) {
            showErrorMessage("Mật khẩu phải trên 6 kí tự")
            return
        }

        if (password != confirmPassword) {
            showErrorMessage("Nhập lại mật khẩu không hợp lệ")
            return
        }

        if (isCheckedClause) {
            showErrorMessage("Chấp nhận điều khoản")
            return
        }

        viewModelScope.launch {
            try {
                showLoading()
                Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            } catch (e: Exception) {
                showErrorMessage(e.message ?: "Đã xảy ra lỗi khi đăng kí")
            } finally {
                hideLoading()
            }
        }
    }

    // Hàm kiểm tra email đơn giản
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}

