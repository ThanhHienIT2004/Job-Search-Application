package com.mobile.jobsearchapplication.ui.features.jobPostManagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.components.emptyState.EmptyState
import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldMenu

@Composable
fun PostedManagerScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmptyState(
            icon = R.drawable.img_empty_state,
            message = "Chức năng này đang phát triển",
            onClick = {  }
        )
    }

}