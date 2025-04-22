package com.mobile.jobsearchapplication.ui.features.menuUser

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.menuBar.user.MenuBarUser
import com.mobile.jobsearchapplication.ui.components.menuBar.user.MenuBarUserViewModel
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar
import com.mobile.jobsearchapplication.utils.DeviceSizeUtils
import com.mobile.jobsearchapplication.utils.ThemePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MenuUserScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    val userState by viewModel.userState.collectAsState()

    LaunchedEffect(userState) {
        viewModel.loadUserState()
    }

    BaseScreen(
        actionsTop = {
            TitleTopBar(text = "Menu", modifier = Modifier.padding(start = 32.dp))
        },
        actionsBot = {
            BottomNavBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
                .fillMaxSize()
                .background(Color(0xFFD5D9E0))
        ) {
            TopUserScreen(viewModel, navController)

            CenterUserScreen(
                navController,
                Modifier.align(Alignment.TopCenter)
                    .offset(y = (DeviceSizeUtils.deviceHeight().dp / 7.5f))
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
    viewModel: UserViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userState by viewModel.userState.collectAsState()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.19f)
            .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp)),
        color = Color(0xFF4A5BCB),
    ) {
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.weight(1f)
                    .padding(10.dp, 0.dp)
                    .size(70.dp),
            )

            Column(
                modifier = Modifier.weight(4f)
            ) {
                Spacer(Modifier.height(5.dp))
                Text(
                    text = userState.email,
                    if (!userState.isLoggedIn) {
                        Modifier.clickable {
                            navController.navigate("auth_screen")
                        }
                    }else { modifier },
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Spacer(Modifier.height(10.dp))
                Text("Super Quang",
                    style = MaterialTheme.typography.bodyMedium,
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
    val context = LocalContext.current
    val themePreferences = remember { ThemePreferences(context) }
    val themeState by themePreferences.isDarkTheme.collectAsState(initial = isSystemInDarkTheme())
    val menuBarUserViewModel: MenuBarUserViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    menuBarUserViewModel.listItemForSettings[0].action = {
        coroutineScope.launch {
//            themePreferences.saveThemeToDataStore(!themeState)
            Toast.makeText(context, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show()
        }
    }

    Surface(
        modifier = modifier.fillMaxWidth(0.8f)
            .fillMaxHeight(0.085f)
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
            .fillMaxHeight(0.78f)
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