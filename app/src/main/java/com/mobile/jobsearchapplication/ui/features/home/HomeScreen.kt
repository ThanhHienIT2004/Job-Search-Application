package com.mobile.jobsearchapplication.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.topBar.IconNotification
import com.mobile.jobsearchapplication.ui.components.topBar.SearchButton
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.ui.features.job.SectionListJob
import com.mobile.jobsearchapplication.ui.features.jobCategory.JobCategoryViewModel
import com.mobile.jobsearchapplication.ui.features.jobCategory.SectionJobCategory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    jobCategoryVM: JobCategoryViewModel = viewModel(),
    jobVM: JobViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Refresh state
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    // Load data when the screen is first displayed
    LaunchedEffect(navBackStackEntry?.id) {
        jobCategoryVM.loadJobCategories()
        jobVM.loadJobByCategory()
    }

    // Handle refresh logic with 1s loading animation
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            jobCategoryVM.loadJobCategories()
            jobVM.loadJobByCategory()
            isRefreshing = false
        }
    }

    BaseScreen(
        actionsTop = {
            Spacer(Modifier.weight(1f))
            IconNotification()
            SearchButton(navController)
        },
        actionsBot = {
            BottomNavBar(navController)
        }
    ) { padding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { isRefreshing = true }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item {
                    SectionJobCategory(jobCategoryVM, navController)
                    SectionListJob(jobVM, jobCategoryVM, navController)
                }
            }
        }
    }
}