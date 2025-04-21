package com.mobile.jobsearchapplication.ui.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.topBar.SearchButton
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.ui.features.job.SectionListJob
import com.mobile.jobsearchapplication.ui.features.jobCategory.JobCategoryViewModel
import com.mobile.jobsearchapplication.ui.features.jobCategory.SectionJobCategory

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
            kotlinx.coroutines.delay(2000) // Simulate network delay
            kotlinx.coroutines.delay(1000) // Additional 1s for loading animation
            isRefreshing = false
        }
    }

    BaseScreen(
        actionsTop = {
            Spacer(Modifier.weight(1f))
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