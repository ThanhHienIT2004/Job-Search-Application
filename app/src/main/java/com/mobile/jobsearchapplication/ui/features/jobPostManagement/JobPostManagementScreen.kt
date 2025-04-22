package com.mobile.jobsearchapplication.ui.features.jobPostManagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.emptyState.EmptyState
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar

@Composable
fun JobPostManagementScreen(
    navController: NavController,
    query: String,
    jPMViewModel: JobPostManagementViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        jPMViewModel.loadAppliedUsers(query)
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController, "posted_screen")
            TitleTopBar(text = "Quản lý bài đăng")
        },
        actionsBot = {
            BottomNavBar(navController)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TabsMenuJobPostManagement(jPMViewModel, navController)
        }
    }
}

@Composable
fun TabsMenuJobPostManagement(
    jPMViewModel: JobPostManagementViewModel,
    navController: NavController
) {
    val tabs = listOf("Bài đăng", "Người ứng tuyển")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = Color.Black,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, style = MaterialTheme.typography.titleMedium) }
                )
            }
        }
    }

    SectionLayoutManager(jPMViewModel, navController, selectedTabIndex)
}

@Composable
fun SectionLayoutManager(
    jPMViewModel: JobPostManagementViewModel,
    navController: NavController,
    selectedTabIndex: Int = 0
) {
    val uiState = jPMViewModel.uiState.collectAsState()

    when (uiState.value) {
        is JobPostManagementUiState.Loading -> {
            Text(text = "Loading...")
        }

        is JobPostManagementUiState.Error -> {
            Text(text = "Error: ${(uiState.value as JobPostManagementUiState.Error).message}")
        }

        is JobPostManagementUiState.Success -> {
            val appliedUsers = (uiState.value as JobPostManagementUiState.Success).appliedUsers

            if (appliedUsers.isEmpty()) {
                EmptyState(
                    icon = R.drawable.img_go_log_in,
                    message = "No applied users",
                    onClick = {}
                )
            } else {
                val columnCount = 1
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnCount),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    item {
                        if (selectedTabIndex == 0) {
                            PostedManagerScreen()
                        } else {
                            AppliedUsersManagerScreen(jPMViewModel, navController)
                        }
                    }
                }
            }
        }
    }
}