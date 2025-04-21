package com.mobile.jobsearchapplication.ui.components.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobile.jobsearchapplication.ui.navigation.NavigationRoute.Companion.baseNavController

sealed class Screen(val route: String, val icon: ImageVector) {
    data object Home : Screen("home_screen", Icons.Filled.Home)
    data object PostedJob : Screen("applied_screen", Icons.Filled.Article)
    data object PostJob : Screen("post_screen", Icons.Filled.AddCircle)
    data object Account : Screen("menu_screen", Icons.Filled.AccountCircle)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.PostedJob,
    Screen.PostJob,
    Screen.Account
)

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
){
    val currRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val selectedIndex = bottomNavItems.indexOfFirst { it.route == currRoute }.coerceAtLeast(0)

    Box(
        modifier = modifier
    ) {
        Row{
            bottomNavItems.forEachIndexed { index, item ->
                if (selectedIndex+1 == index) {
                    Box(Modifier.weight(1f)) {
                        ItemNavBarDefault(
                            item = item,
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    baseNavController(navController, item.route)
                                }
                        )
                        ShapeArrowRight(Modifier.align(Alignment.TopStart))
                    }
                } else if (selectedIndex>0 && selectedIndex-1 == index) {
                    Box(Modifier.weight(1f)) {
                        ItemNavBarDefault(
                            item = item,
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    baseNavController(navController, item.route)
                                }
                        )
                        ShapeArrowLeft(Modifier.align(Alignment.BottomEnd))
                    }
                }
                else {
                    ItemNavBarDefault(
                        item = item,
                        modifier = Modifier.weight(1f)
                            .clickable {
                                baseNavController(navController, item.route)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemNavBarDefault(
    item: Screen,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier.height(54.dp).padding(horizontal = 2.dp)
            .background(Color(0xFF2C2F2F), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(48.dp)
                .background(Color(0xFFF8F7F7), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon, contentDescription = "",
                modifier = Modifier.size(42.dp), tint = Color(0xFF2C2F2F)
            )
        }
    }
}

@Composable
fun ShapeArrowRight(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Card(
            modifier = Modifier.width(26.dp).height(24.dp)
                .offset(x = (-2).dp, y = (-5).dp),
            shape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) { }
        Card(
            modifier = Modifier.width(38.dp).height(16.dp)
                .offset(x = (-18).dp)
                .align(Alignment.TopStart),
            shape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp),
            colors = CardDefaults.cardColors(Color(0xFF2C2F2F))
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos, contentDescription = "",
                modifier = Modifier.size(32.dp).offset(x = 10.dp), tint = Color.White
            )
        }
    }
}

@Composable
fun ShapeArrowLeft(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Card(
            modifier = Modifier.width(24.dp).height(24.dp)
                .offset(x = 12.dp, y = 4.dp),
            shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) { }
        Card(
            modifier = Modifier.width(34.dp).height(16.dp)
                .offset(x = 16.dp, y = 8.dp)
                .align(Alignment.TopStart),
            shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp),
            colors = CardDefaults.cardColors(Color(0xFF2C2F2F))
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "",
                modifier = Modifier.size(32.dp).offset(x = (-5).dp), tint = Color.White
            )
        }
    }
}
