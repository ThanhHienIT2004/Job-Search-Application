package com.mobile.jobsearchapplication.ui.features.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldCustom
import com.mobile.jobsearchapplication.ui.features.auth.AuthViewModel
import com.mobile.jobsearchapplication.ui.features.auth.register.RegisterViewModel

@Composable
fun LoginScreen(
    authVM: AuthViewModel,
    loginVM: LoginViewModel,
    registerVM: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val registerState by registerVM.registerState.collectAsState()
    val loginState by loginVM.loginState.collectAsState()

    if (registerState.email.isNotBlank() && registerState.password.isNotBlank() && registerState.isRegisterSuccess) {
        loginVM.emailField.onValueChange(registerState.email)
        loginVM.passwordField.onValueChange(registerState.password)
    }

    loginVM.emailField.value = loginState.email
    loginVM.passwordField.value = loginState.password

    loginVM.emailField.isError = loginState.isErrorEmail
    loginVM.passwordField.isError = loginState.isErrorPassword

    LaunchedEffect(loginState.isLoggedSucess) {
        if (loginState.isLoggedSucess)
            authVM.onSuccessLogin(true)
    }

    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        loginVM.listTextFieldLogin.forEach {
            TextFieldCustom(
                Modifier.padding(top = 10.dp), model = it
            )
        }

        TextButton(
            onClick = { /* Xử lý quên mật khẩu */ },
            modifier = Modifier.fillMaxWidth(0.9f)
                .wrapContentWidth(Alignment.Start)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_forget_password),
                contentDescription = "Icon Forget Password",
                modifier = Modifier.padding(end = 10.dp).size(22.dp),
                tint = Color.Gray
            )
            Text("Quên mật khẩu?", color = Color.Gray, fontSize = 14.sp)
        }

        Button(
            onClick = {
                if (loginVM.doCheckError()) {
                    loginVM.signInWithEmail()
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
                "Đăng nhập",
                fontSize = 20.sp, color = Color.Black
            )
        }
    }

}