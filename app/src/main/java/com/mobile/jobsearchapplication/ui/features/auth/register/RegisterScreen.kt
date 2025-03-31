package com.mobile.jobsearchapplication.ui.features.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel()
) {
    // Lấy các trạng thái từ ViewModel
    val fullName = viewModel.fullName
    val email = viewModel.email
    val password = viewModel.password
    val confirmPassword = viewModel.confirmPassword
    val isCheckedClause = viewModel.isCheckedClause
    val errorMessage = viewModel.errorMessage
    val isLoading = viewModel.loading

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Trường Họ và tên
            OutlinedTextField(
                value = fullName,
                onValueChange = { viewModel.onFullNameChanged(it) },
                label = { Text("Họ và tên") },
                modifier = Modifier.fillMaxWidth(),
                isError = fullName.isBlank()
            )
            if (fullName.isBlank()) {
                Text(
                    text = "Họ và tên không được để trống",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Trường Email
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                placeholder = { Text("Abc123@gmail.com") },
                modifier = Modifier.fillMaxWidth(),
                isError = email.isBlank() || !viewModel.isValidEmail(email)
            )
            if (email.isBlank() || !viewModel.isValidEmail(email)) {
                Text(
                    text = "Email không hợp lệ",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Trường Mật khẩu
            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Mật khẩu") },
//                supportingText = { Text("Chữ cái viết hoa, số") },
                modifier = Modifier.fillMaxWidth(),
                isError = password.isBlank() || password.length < 6
            )
            if (password.isBlank() || password.length < 6) {
                Text(
                    text = "Mật khẩu phải có ít nhất 6 ký tự",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Trường Xác nhận mật khẩu
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                label = { Text("Xác nhận mật khẩu") },
                modifier = Modifier.fillMaxWidth(),
                isError = confirmPassword.isNotBlank() && password != confirmPassword
            )
            if (confirmPassword.isNotBlank() && password != confirmPassword) {
                Text(
                    text = "Mật khẩu không khớp",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Checkbox và điều khoản
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isCheckedClause,
                    onCheckedChange = { viewModel.onCheckedClause() }
                )
                TextButton(onClick = { /* Xử lý mở điều khoản */ }) {
                    Text("Tôi đồng ý với Điều khoản", color = Color.Gray, fontSize = 12.sp)
                }
            }
            if (!isCheckedClause) {
                Text(
                    text = "Bạn phải chấp nhận điều khoản",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nút Đăng ký
            Button(
                onClick = { viewModel.register() },
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Text("Đăng ký")
            }

            // Hiển thị thông báo lỗi từ ViewModel (nếu có)
            errorMessage.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                error.value?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Loading overlay
        if (isLoading.value == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)), // nửa trong suốt
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator()
            }
        }
    }
}

