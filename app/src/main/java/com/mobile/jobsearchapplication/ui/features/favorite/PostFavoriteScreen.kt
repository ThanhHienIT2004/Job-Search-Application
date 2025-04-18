package com.mobile.jobsearchapplication.ui.features.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.components.PostItemList
import com.mobile.jobsearchapplication.ui.features.user.UserViewModel

@Composable
fun FavoriteJobsScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    var isExpanded by remember { mutableStateOf(false) }

    val jobs = listOf("Lập trình mobile frontend", "Job 2", "Job 3","job 4","job 5")

    jobs.forEach { job ->
        PostItemList(navController, jobTitle = job)
    }

}

