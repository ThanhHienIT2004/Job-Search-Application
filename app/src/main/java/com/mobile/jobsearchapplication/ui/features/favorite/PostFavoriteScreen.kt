package com.mobile.jobsearchapplication.ui.features.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.components.PostItemList
import com.mobile.jobsearchapplication.ui.features.user.UserViewModel

@Composable
fun PostFaveriteScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    var isExpanded by remember { mutableStateOf(false) }

                                                                                                                                   Column(modifier = Modifier.padding(16.dp)) {
//        TitleSection("Việc dành cho bạn", isExpanded = isExpanded) {
//            isExpanded = it
//        }

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            val jobs = listOf("Lập trình mobile frontend", "Job 2", "Job 3","job 4","job 5")

            items(jobs.size) { index ->
                PostItemList(navController, jobTitle = jobs[index])
            }
        }
    }
}

