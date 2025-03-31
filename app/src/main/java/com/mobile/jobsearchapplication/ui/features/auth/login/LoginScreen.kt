package com.mobile.jobsearchapplication.ui.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginForm() {
    Box{
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Email") })
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Mật khẩu") })

            // 👉 Nút "Quên mật khẩu?"
            TextButton(onClick = { /* Xử lý quên mật khẩu */ },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentWidth(Alignment.Start)) {
                Text("Quên mật khẩu?", color = Color.Blue, fontSize = 9.sp)
            }

            Button(onClick = { /* Xử lý đăng nhập */ }) {
                Text("Đăng nhập")
            }
        }
    }
}


