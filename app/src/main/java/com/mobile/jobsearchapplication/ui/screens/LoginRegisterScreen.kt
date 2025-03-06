package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginRegisterScreen() {
    var isLogin by remember { mutableStateOf(true) }
    var rawOffset by remember { mutableStateOf(0f) }

    val animatedOffset by animateFloatAsState(
        targetValue = if (rawOffset < 50f) 0f else 100f,
        animationSpec = tween(durationMillis = 10),
        label = "",
    )

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(50.dp))

            Box( // Toggle login and register
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(50.dp))
                    .border(2.dp, Color.LightGray, RoundedCornerShape(50.dp))
                    .background(Color.LightGray)
                    .padding(2.dp)
                    .offset(x = animatedOffset.dp)
                    .draggable(
                        state = rememberDraggableState { delta ->
                            rawOffset = (rawOffset + delta).coerceIn(0f, 100f)
                            isLogin = rawOffset < 50f
                        },
                        Orientation.Horizontal
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .clip(RoundedCornerShape(50.dp))
                        .border(2.dp, Color(0xFF6A5B91), RoundedCornerShape(50.dp))
                        .background(Color(0xFF6A5B91))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(if (isLogin) "Đăng nhập" else "Đăng kí", color = Color.White, fontSize = 18.sp)

                }
            }
            Spacer(Modifier.height(5.dp))
            // form login or register
            if (isLogin) LoginForm() else RegisterScreen()
        }
    }
}

@Composable
fun LoginForm() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp
        ) {
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

                Spacer(modifier = Modifier.height(15.dp))
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp, // Độ dày của đường
                    modifier = Modifier.fillMaxWidth(0.8f) // Chiều dài full màn hình
                )


                Text("Hoặc đăng nhập với", fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Google Login Button
                    Button(
                        onClick = { /* Xử lý đăng nhập Google */ },
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Google",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Facebook Login Button
                    Button(
                        onClick = { /* Xử lý đăng nhập Facebook */ },
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_facebook),
                            contentDescription = "Facebook",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterScreen() {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Họ và tên") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mật khẩu") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Xác nhận mật khẩu") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Xử lý đăng ký */ },
                    modifier = Modifier.fillMaxWidth(0.4f)
                ) {
                    Text("Đăng ký")
                }
            }
        }
    }
}



