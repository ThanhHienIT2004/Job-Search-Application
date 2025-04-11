package com.mobile.jobsearchapplication.ui.features.auth.login

import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.auth.CreateUserRequest
import com.mobile.jobsearchapplication.data.repository.auth.AuthRepository
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.ui.components.textField.auth.TextFieldAuthModel
import com.mobile.jobsearchapplication.ui.features.auth.AuthViewModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.auth
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import com.mobile.jobsearchapplication.utils.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class LoginState(
    var email: String = "",
    var password: String = "",
    var isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedSucess: Boolean = false,
    val errorMessage: String = ""
)

class LoginViewModel() : AuthViewModel() {
    private val authRepository = AuthRepository(RetrofitClient.authApiService)

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    val emailField = TextFieldAuthModel(
        value = "",
        onValueChange = { onEmailChanged(it) },
        label = "Email",
        leadingIcon = R.drawable.ic_email_auth,
        messageError = "Email Không hợp lệ"
    )

    val passwordField = TextFieldAuthModel(
        value = "",
        onValueChange = { onPasswordChanged(it) },
        label = "Mật khẩu",
        leadingIcon = R.drawable.ic_password_auth,
        trailingIcons = listOf(
            R.drawable.ic_visibility_off, R.drawable.ic_visibility
        ),
        messageError = "Hãy nhập mật khẩu",
        isPasswordField = true,
        isImeActionDone = true
    )

    val listTextFieldLogin = listOf(
        emailField, passwordField
    )

    private fun onEmailChanged(newEmail: String) {
        _loginState.value = _loginState.value.copy(
            email = newEmail
        )
    }

    private fun onPasswordChanged(newPassword: String) {
        _loginState.value = _loginState.value.copy(
            password = newPassword
        )
    }

    fun doCheckError(): Boolean {
        if (
            _loginState.value.email.isBlank()
            || (_loginState.value.email.isNotBlank() && !isValidEmail(_loginState.value.email))
        ) {
            _loginState.value = _loginState.value.copy(isErrorEmail = true)
            return false
        }
        _loginState.value = _loginState.value.copy(isErrorEmail = false)

        if (_loginState.value.password.isBlank()) {
            _loginState.value =  _loginState.value.copy(isErrorPassword = true)
            return false
        }
        _loginState.value =  _loginState.value.copy(isErrorPassword = false)

        return true
    }

    fun login() {
        viewModelScope.launch {
            try {
                showLoading()

                val result = auth.signInWithEmailAndPassword(
                    _loginState.value.email,
                    _loginState.value.password
                ).await()

                val request = CreateUserRequest(getLoggedInUserId().toString())
                val response = authRepository.createUser(request)

                if (result != null) {
                    _loginState.value = _loginState.value.copy(isLoggedSucess = true)
                } else {
                    _loginState.value = _loginState.value.copy(isLoggedSucess = false)
                }

                hideLoading()

            } catch (e: Exception) {
                showErrorMessage(e.message ?: "Đã xảy ra lỗi khi đăng nhập")
            } finally {
                hideLoading()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}