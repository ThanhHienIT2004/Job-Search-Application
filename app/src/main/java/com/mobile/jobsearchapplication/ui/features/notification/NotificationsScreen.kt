package com.mobile.jobsearchapplication.ui.features.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Updater
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NotificationScreen() {
    val notiVM: NotificationViewModel = viewModel()
    val notiState by notiVM.notificationsState.collectAsState()

    LaunchedEffect(notiState){
        Updater<T>
    }
}