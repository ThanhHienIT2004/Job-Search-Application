package com.mobile.jobsearchapplication.ui.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldAuth()

        Spacer(modifier = Modifier.height(8.dp))

        // 👉 Nút "Quên mật khẩu?"
        TextButton(
            onClick = { /* Xử lý quên mật khẩu */ },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentWidth(Alignment.Start)
        ) {
            Text("Quên mật khẩu?", color = Color.Blue, fontSize = 9.sp)
        }

        Button(onClick = { /* Xử lý đăng nhập */ }) {
            Text("Đăng nhập")
        }
    }

}

@Composable
fun TextFieldAuth(viewModel: LoginViewModel = viewModel()) {
    viewModel.textFieldLoginItems.forEach {
        OutlinedTextField(
            value = it.value,
            onValueChange = it.onClick,
            label = { Text(text = it.value) },
            modifier = Modifier.fillMaxWidth(0.9f),
            isError = viewModel.email.isBlank()
        )
        if (viewModel.email.isBlank()) {
            Text(
                text = it.errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}