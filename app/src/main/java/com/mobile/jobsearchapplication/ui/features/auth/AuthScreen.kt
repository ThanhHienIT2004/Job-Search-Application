package com.mobile.jobsearchapplication.ui.features.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.features.auth.login.LoginScreen
import com.mobile.jobsearchapplication.ui.features.auth.login.LoginViewModel
import com.mobile.jobsearchapplication.ui.features.auth.register.RegisterScreen
import com.mobile.jobsearchapplication.ui.features.auth.register.RegisterViewModel
import com.mobile.jobsearchapplication.utils.DeviceSizeUtils
import com.mobile.jobsearchapplication.utils.GoogleSignInUtils

@Composable
fun AuthScreen(
    navController: NavController
) {
    val focusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F4FD))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        TopBackgroundAuth(navController)

        Spacer(Modifier.height(30.dp))

        BotBackGroundAuth()
    }
}


@Composable
fun TopBackgroundAuth(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val authState by viewModel.authState.collectAsState()
    // title
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
        color = Color(0xFFA9B9FF)
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Welcome Back",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.White
            )
        }
    }
    // toggle button
    Row {
        if (authState.isLoginScreen) {
            ToggleButtonAuth()
            if (!authState.isDraggingButton) IconSingUpAuth(navController)
        } else {
            IconSingUpAuth(navController)
            ToggleButtonAuth()
        }
    }
}

@Composable
fun ToggleButtonAuth(
    viewModel: AuthViewModel = viewModel()
) {
    val authState by viewModel.authState.collectAsState()
    val maxDeviceWidth = DeviceSizeUtils.deviceWidthInPx()
    var offsetXButton by remember { mutableFloatStateOf(0f) }
    val offsetXBegin = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (authState.isFirstLoad) {
            repeat(1){
                offsetXBegin.animateTo(maxDeviceWidth / 19f, animationSpec = tween(500))
                offsetXBegin.animateTo(0f, animationSpec = tween(1500))
            }
            viewModel.onFirstLoad()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(if (authState.isLoginScreen) 0.6f else 1f)
            .offset { IntOffset((offsetXButton + offsetXBegin.value).toInt(), (-1).dp.roundToPx()) }
            .draggable(
                state = rememberDraggableState { delta ->
                    offsetXButton += delta
                },
                orientation = Orientation.Horizontal,
                onDragStarted = {
                    viewModel.onDragging()
                },
                onDragStopped = {
                    if (offsetXButton < (maxDeviceWidth / 4f)) {
                        offsetXButton = 0f
                        viewModel.onDragButton(true)
                    } else {
                        offsetXButton = maxDeviceWidth / 40f
                        viewModel.onDragButton(false)
                    }
                }
            )
    )  {
        Image(
            painter = painterResource(R.drawable.ic_authscreen),
            contentDescription = "Around Button",
        )

        Text(
            text = authState.textButton,
            fontWeight = FontWeight.Bold, fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-5).dp)
        )
    }

}

@Composable
fun IconSingUpAuth(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authState by viewModel.authState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}

    LaunchedEffect(authState.isSuccessLogin) {
        if (authState.isSuccessLogin) {
            navController.navigate("home_screen")
        }
    }

    Row(
        modifier = Modifier.height(56.dp)
            .fillMaxWidth(if (authState.isLoginScreen) 1f else 0.4f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        viewModel.iConSingUpItems.forEach { icon ->
            IconButton (
                onClick = {
                    GoogleSignInUtils.doGoogleSignIn(
                        context = context,
                        scope = scope,
                        launcher = launcher,
                        login = {
                            viewModel.onSuccessLogin()
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                modifier = Modifier
                    .border(2.dp, Color.Gray, CircleShape)
            ) {
                Image(
                    painter = painterResource(icon.icon),
                    contentDescription = icon.text,
                    modifier = Modifier.padding(2.dp)
                        .size(64.dp)
                )
            }
        }
    }
}

@Composable
fun BotBackGroundAuth(
    authVM: AuthViewModel = viewModel()
) {
    val loginVM: LoginViewModel = viewModel()
    val registerVM: RegisterViewModel = viewModel()
    val authState by authVM.authState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight()
                .padding(8.dp),
            shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(Modifier.height(10.dp))

                if (authState.isLoginScreen) {
                    LoginScreen(registerVM)
                }
                else {
                    RegisterScreen(authVM, registerVM)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrevAuthScreen() {
    AuthScreen(navController = rememberNavController())
}