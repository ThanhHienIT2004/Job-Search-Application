package com.mobile.jobsearchapplication.ui.components.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable


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