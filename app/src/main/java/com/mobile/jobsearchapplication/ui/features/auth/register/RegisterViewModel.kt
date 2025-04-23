package com.mobile.jobsearchapplication.ui.features.auth.register

import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class RegisterState(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    val isCheckedClause: Boolean = false,
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isErrorConfirmPassword: Boolean = false,
    val isErrorClause: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val errorMessage: String = ""
)

class RegisterViewModel : BaseViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    val emailField = TextFieldModel(
        value = "",
        onValueChange = { onEmailChanged(it) },
        label = "Email",
        leadingIcon = R.drawable.ic_email_auth,
        messageError = "Email Không hợp lệ"
    )

    val passwordField = TextFieldModel(
        value = "",
        onValueChange = { onPasswordChanged(it) },
        label = "Mật khẩu",
        leadingIcon = R.drawable.ic_password_auth,
        trailingIcons = listOf(
            R.drawable.ic_visibility_off, R.drawable.ic_visibility
        ),
        messageError = "Hãy nhập mật khẩu",
        isPasswordField = true,
        isImeActionDone = false
    )

    val confirmPasswordField = TextFieldModel(
        value = "",
        onValueChange = { onConfirmPasswordChanged(it) },
        label = "Nhập lại mật khẩu",
        leadingIcon = R.drawable.ic_confirm_password_auth,
        trailingIcons = listOf(
            R.drawable.ic_visibility_off, R.drawable.ic_visibility
        ),
        messageError = "Mật khẩu nhập lại không đúng",
        isPasswordField = true,
        isImeActionDone = true
    )

    val listTextFieldRegister = listOf(
        emailField, passwordField, confirmPasswordField
    )

    private fun onEmailChanged(newEmail: String) {
        _registerState.value = _registerState.value.copy(
            email = newEmail
        )
    }

    private fun onPasswordChanged(newPassword: String) {
        _registerState.value = _registerState.value.copy(
            password = newPassword
        )
    }

    private fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _registerState.value = _registerState.value.copy(
            confirmPassword = newConfirmPassword
        )
    }

    fun onCheckedClause() {
        _registerState.value = _registerState.value.copy(
            isCheckedClause = !_registerState.value.isCheckedClause
        )
    }

    fun doCheckError(): Boolean {
        if (
            _registerState.value.email.isBlank()
            || (_registerState.value.email.isNotBlank() && !isValidEmail(_registerState.value.email))
        ) {
            _registerState.value = _registerState.value.copy(isErrorEmail = true)
            return false
        }
        _registerState.value = _registerState.value.copy(isErrorEmail = false)

        if (_registerState.value.password.isBlank()) {
            _registerState.value =  _registerState.value.copy(isErrorPassword = true)
            return false
        }
        _registerState.value =  _registerState.value.copy(isErrorPassword = false)

        if (_registerState.value.confirmPassword.isNotBlank() && _registerState.value.password != _registerState.value.confirmPassword) {
            _registerState.value =  _registerState.value.copy(isErrorConfirmPassword = true)
            return false
        }
        _registerState.value =  _registerState.value.copy(isErrorConfirmPassword = false)

        if (!_registerState.value.isCheckedClause) {
            _registerState.value =  _registerState.value.copy(isErrorClause = true)
            return false
        }
        _registerState.value =  _registerState.value.copy(isErrorClause = false)

        return true
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()

                showLoading()
                if (result != null) {
                    _registerState.value = _registerState.value.copy(isRegisterSuccess = true)
                } else {
                    _registerState.value = _registerState.value.copy(isRegisterSuccess = false)
                }
                hideLoading()

            } catch (e: Exception) {
                showErrorMessage(e.message ?: "Đã xảy ra lỗi khi đăng kí")
            } finally {
                hideLoading()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

