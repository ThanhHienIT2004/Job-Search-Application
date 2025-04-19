package com.mobile.jobsearchapplication.ui.features.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.MenuNavBar
import com.mobile.jobsearchapplication.ui.components.dropdownMenu.MenuGrid
import com.mobile.jobsearchapplication.ui.components.menuBar.saved.MenuBarSaved
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar
import com.mobile.jobsearchapplication.ui.features.job.JobItem
import com.mobile.jobsearchapplication.ui.features.job.JobUiState
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.ui.navigation.NavigationRoute.Companion.baseNavController


@Composable
fun SavedScreen(
    navController: NavController,
    savedVM: SavedViewModel = viewModel(),
    jobVM: JobViewModel = viewModel()
) {
    val jobUiState = jobVM.uiState.collectAsState()
    val savedUiState = savedVM.uiState.collectAsState()
    val tabSaved = savedVM.currentTab.collectAsState()
    val queryRoute = navController.currentBackStackEntryAsState().value?.destination?.route.toString()
    savedVM.onTabChanged(queryRoute)
    var columnCount by remember { mutableIntStateOf(2) }

    LaunchedEffect(Unit) {
        savedVM.loadFavoriteJobs()
        savedVM.loadPostedJobs()
        jobVM.loadFavoriteJobs()
    }

    BaseScreen(
        actionsTop = {
            TitleTopBar(
                modifier = Modifier.padding(start = 32.dp),
                text = "Bài đăng của bạn"
            )
        },
        actionsBot = { MenuNavBar(navController) }
    ) { padding ->
        Column (
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(70.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuBarSaved(
                    selectedTab = tabSaved.value,
                    onTabSelected = { tab ->
                        savedVM.onTabChanged(tab)
                        when (tab) {
                            "favorite_screen" -> {
                                savedVM.loadFavoriteJobs()
                                jobVM.loadFavoriteJobs()
                            }
                            "posted_screen" -> {
                                savedVM.loadPostedJobs()
                                jobVM.loadFavoriteJobs()
                            }
                        }
                    },
                    modifier = Modifier.weight(7f)
                )
                VerticalDivider(thickness = 0.6.dp)
                MenuGrid( { columnCount = it } , modifier = Modifier.weight(1f).padding(horizontal = 2.dp))
            }
            HorizontalDivider()

            when{
                savedUiState.value is SavedUiState.Loading || jobUiState.value is JobUiState.Loading -> {
                    Text("This is loading")
                }
                jobUiState.value is JobUiState.Error -> { (jobUiState.value as JobUiState.Error).message }
                savedUiState.value is SavedUiState.Error -> { (savedUiState.value as SavedUiState.Error).message }
                savedUiState.value is SavedUiState.Success && jobUiState.value is JobUiState.Success -> {
                    val listItem = when(tabSaved.value) {
                        "applied_screen" -> emptyList()
                        "posted_screen" -> (savedUiState.value as SavedUiState.Success).postedJobs
                        "favorite_screen" -> (savedUiState.value as SavedUiState.Success).favoriteJobs
                        else -> emptyList()
                    }

                    // Kiểm tra danh sách
                    if (listItem.isNullOrEmpty()) {
                        Text("No data")
                    } else {
                        SectionListSaved(
                            jobs =  listItem,
                            jobVM = jobVM,
                            navController = navController,
                            columnCount = columnCount
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionListSaved(
    jobs: List<Job>,
    jobVM: JobViewModel,
    navController: NavController,
    columnCount: Int = 2,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(jobs.size) { job ->
            JobItem(
                jobVM = jobVM,
                job = jobs[job],
                onClick = {
                    baseNavController(navController,"job_detail_screen/${jobs[job].id}")
                },
                isEnableIcon = columnCount != 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (columnCount == 1) {
                            Modifier.aspectRatio(1.8f)
                        } else {
                            Modifier.aspectRatio(1f)
                        }
                    )
            )
        }
    }

}