package com.mobile.jobsearchapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BackButton(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() }
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "BtnBack"
        )
    }
}

@Composable
fun FilterButton() {
    IconButton(
        onClick = {  }
    ) {
        Icon(
            imageVector = Icons.Filled.FilterList,
            contentDescription = "BtnBack"
        )
    }
}