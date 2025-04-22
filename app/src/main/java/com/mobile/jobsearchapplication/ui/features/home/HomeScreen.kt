package com.mobile.jobsearchapplication.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.topBar.IconNotification
import com.mobile.jobsearchapplication.ui.components.topBar.SearchButton
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.ui.features.job.SectionListJob
import com.mobile.jobsearchapplication.ui.features.jobCategory.JobCategoryViewModel
import com.mobile.jobsearchapplication.ui.features.jobCategory.SectionJobCategory
import com.mobile.jobsearchapplication.ui.features.notification.NotificationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    jobCategoryVM: JobCategoryViewModel = viewModel(),
    jobVM: JobViewModel = viewModel(),
    notificationVM: NotificationViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry?.id) {
        jobCategoryVM.loadJobCategories()
        jobVM.loadJobByCategory()
        notificationVM.loadNotification("g3DCV3byPnau4HXRhQYso4iTBoE2")
    }

    BaseScreen(
        actionsTop = {
            IconNotification()
            Spacer(Modifier.weight(0.5f))
            SearchButton(navController)

        },
        actionsBot = {
            BottomNavBar(navController)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
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



