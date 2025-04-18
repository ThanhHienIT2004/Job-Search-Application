package com.mobile.jobsearchapplication.ui.features.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.components.menuBar.user.MenuBarUser
import com.mobile.jobsearchapplication.utils.DeviceSizeUtils

@Composable
fun UserScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    BaseScreen(
        actionsBot = {
            BottomNavBarCustom(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
                .fillMaxSize()
                .background(Color(0xFFB6C3E3))
        ) {
            TopUserScreen(navController)

            CenterUserScreen(
                navController,
                Modifier.align(Alignment.TopCenter)
            )

            BottomUserScreen(
                navController,
                Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun TopUserScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<UserViewModel>()
    val context = LocalContext.current
    val userState by viewModel.userState.collectAsState()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.23f)
            .zIndex(0f)
            .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp)),
        color = Color(0xFF4A5BCB),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Avatar",
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .size(70.dp),
            )

            Column {
                Text(
                    text =  userState.email,
                    if (!userState.isLoggedIn) {
                        Modifier.clickable { navController.navigate("auth_screen") }
                    }else { modifier },
                    fontSize = 24.sp, fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(20.dp))
                Text("Super Quang",
                    fontSize = 15.sp, fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CenterUserScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.07f)
            .offset(y = (DeviceSizeUtils.deviceHeight().dp / 5f))
            .zIndex(1f)
            .clip(RoundedCornerShape(32.dp)),
        color = Color(0xFFF6F9FC)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuBarUser(true, navController)
        }
    }
}

@Composable
fun BottomUserScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .clip(RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)),
        color = Color(0xFFE9ECF8)
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))
            MenuBarUser(false, navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevUser() {
    UserScreen(navController = rememberNavController())
}