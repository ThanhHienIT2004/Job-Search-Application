package com.mobile.jobsearchapplication.ui.features.auth.register

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile.jobsearchapplication.ui.components.textField.auth.TextFieldAuth
import com.mobile.jobsearchapplication.ui.features.auth.AuthViewModel

@Composable
fun RegisterScreen(
    authVM: AuthViewModel,
    registerVM: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val registerState by registerVM.registerState.collectAsState()
    val context = LocalContext.current

    registerVM.emailField.value = registerState.email
    registerVM.passwordField.value = registerState.password
    registerVM.confirmPasswordField.value = registerState.confirmPassword

    registerVM.emailField.isError = registerState.isErrorEmail
    registerVM.passwordField.isError = registerState.isErrorPassword
    registerVM.confirmPasswordField.isError = registerState.isErrorConfirmPassword

    LaunchedEffect(registerState.isRegisterSuccess) {
        if (registerState.isRegisterSuccess) {
            Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show()
            authVM.onDragButton(true)
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        registerVM.listTextFieldRegister.forEach {
            TextFieldAuth(
                Modifier.padding(top = 10.dp), it
            )
        }

        // Checkbox và điều khoản
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = registerState.isCheckedClause,
                onCheckedChange = { registerVM.onCheckedClause() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Gray
                )
            )
            Text ("Tôi đồng ý với điều khoản", color = Color.Gray, fontSize = 14.sp)
        }
        if (registerState.isErrorClause) {
            Text(
                text = "Bạn phải chấp nhận điều khoản",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Nút Đăng ký
        Button(
            onClick = {
                if (registerVM.doCheckError()) {
                    registerVM.register(registerState.email, registerState.password)
                }
            },
            modifier = Modifier.padding(0.dp, 20.dp)
                .fillMaxWidth(0.6f)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.Gray),
            ) {
            Text(
                "Đăng ký",
                fontSize = 20.sp, color = Color.Black
            )
        }
        registerState.errorMessage.let { error ->
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Loading overlay
    if (registerState.isLoading) {
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

