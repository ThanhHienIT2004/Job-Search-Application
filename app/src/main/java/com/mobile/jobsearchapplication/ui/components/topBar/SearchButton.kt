package com.mobile.jobsearchapplication.ui.components.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.theme.White70

@Composable
fun SearchButton(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.ic_search),
        tint = Color.White,
        contentDescription = "Search Button",
        modifier = modifier.padding(horizontal = 10.dp)
            .size(32.dp)
            .clickable {
                navController.navigate("search_screen")
            }
    )
}