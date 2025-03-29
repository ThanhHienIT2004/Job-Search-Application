package com.mobile.jobsearchapplication.ui.features.auth

import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.features.auth.login.LoginForm
import com.mobile.jobsearchapplication.ui.features.auth.register.RegisterScreen
import com.mobile.jobsearchapplication.utils.DeviceWidthUtils

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    Column (
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFE7EEFF))
    ) {
        TopBgAuthScreen()

        Spacer(Modifier.height(5.dp))

        // form login or registerUser
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (viewModel.isLogin) LoginForm() else RegisterScreen()

            Divider(
                color = Color.Gray,
                thickness = 1.dp, // Độ dày của đường
                modifier = Modifier.fillMaxWidth(0.8f) // Chiều dài full màn hình
            )
            Text("Hoặc đăng nhập với", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun TopBgAuthScreen(viewModel: AuthViewModel = viewModel()) {
    val maxDeviceWidth = DeviceWidthUtils.deviceWidth()
    var offsetXButton by remember { mutableFloatStateOf(0f) }
    var offsetXIconAuth by remember { mutableFloatStateOf(0f) }
    val offsetXBegin = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (viewModel.isShowGuide) {
            offsetXBegin.animateTo(maxDeviceWidth/5f, animationSpec = tween(1000))
            offsetXBegin.animateTo(0f, animationSpec = tween(500))
            viewModel.isShowGuide = false
        }
    }

    // title
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
        color = Color(0xFFA9B9FF)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Welcome Back",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.White
            )
        }
    }
    // Button
    Row {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .offset {
                    IntOffset(offsetXButton.toInt(), (-1).dp.roundToPx())
//                    IntOffset(offsetXBegin.value.toInt(), (-1).dp.roundToPx())
                }
                .draggable(
                    state = rememberDraggableState { delta ->
                        offsetXButton += delta
                    },
                    orientation = Orientation.Horizontal,
                    onDragStarted = {
                        viewModel.isShowGuide = false
                    },
                    onDragStopped = {
                        if (offsetXButton < (maxDeviceWidth / 2)) {
                            offsetXButton = 0f
                            offsetXIconAuth = maxDeviceWidth.toFloat()
                            viewModel.onDragLogin(true)
                        } else {
                            offsetXButton = maxDeviceWidth.toFloat()
                            offsetXIconAuth = 0f
                            viewModel.onDragLogin(false)
                        }
                    }
                )
        )  {
            Image(
                painter = painterResource(R.drawable.ic_authscreen),
                contentDescription = "Around Button",
            )

            Text(
                text = viewModel.textButtonLogin,
                fontWeight = FontWeight.Bold, fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-5).dp)
            )
        }

        //
        IconSingUpAuth()
    }
}

@Composable
fun IconSingUpAuth(
//    offsetXIconAuth: ,
    viewModel: AuthViewModel = viewModel()
) {
    val offsetXIconAuth: Int = 0
    Row(
        modifier = Modifier.fillMaxWidth().height(52.dp)
            .offset(-(DeviceWidthUtils.deviceWidth().dp) / 1.8f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        viewModel.iConSingUpItems.forEach { icon ->
            IconButton (
                onClick = { /* Xử lý đăng nhập Facebook */ },
                colors = IconButtonDefaults.iconButtonColors(Color(0xFFE5EDFF)),
                modifier = Modifier
                    .border(1.dp, Color.Gray, CircleShape)
            ) {
                Image(
                    painter = painterResource(icon.icon),
                    contentDescription = icon.text,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevAuthScreen() {
    AuthScreen(navController = rememberNavController())
}