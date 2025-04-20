package com.mobile.jobsearchapplication.ui.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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

    LaunchedEffect(navBackStackEntry?.id) {
        jobCategoryVM.loadJobCategories()
        jobVM.loadJobByCategory()
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
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
//                .background(Color(0xFFF9FAFC)),
            contentPadding = PaddingValues(12.dp),
        ) {
            item {
                // Danh mục việc làm
                SectionJobCategory(
                    jobCategoryVM,
                    navController
                )
                // Việc dành cho bạn
                SectionListJob(
                    jobVM,
                    jobCategoryVM,
                    navController
                )
            }
        }
    }
}



