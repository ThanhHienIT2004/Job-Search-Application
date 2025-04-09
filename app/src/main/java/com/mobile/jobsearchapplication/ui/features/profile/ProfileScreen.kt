package com.mobile.jobsearchapplication.ui.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.icon.IconEditProfile
import com.mobile.jobsearchapplication.ui.components.menu_bar.profile.MenuBarProfile
import com.mobile.jobsearchapplication.ui.components.menu_bar.profile.MenuItemProfile

@Composable
fun ProfileScreen(navController: NavController) {
    BaseScreen(
        showBackButton = true,
        onBackClick = { navController.navigate("account") },
        title = "Hồ sơ cá nhân"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TopProfileScreen()

            BottomProfileScreen()
        }
    }
}

@Composable
fun TopProfileScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(130.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(3.dp, Color.Gray, CircleShape)
                .shadow(8.dp, CircleShape)
        )

        Spacer(Modifier.weight(0.7f))
//        IconEditProfile( { } )
    }
}


@Composable
fun BottomProfileScreen(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFFCFCFF), RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .shadow(2.dp, RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuBarProfile()

//        MenuItemProfile(Modifier.offset(y = (-6).dp))
    }
}