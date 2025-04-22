package com.mobile.jobsearchapplication.ui.features.jobPostManagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.room.Query
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar
import com.mobile.jobsearchapplication.ui.features.post.JobPost
import com.mobile.jobsearchapplication.ui.features.post.PostScreen
import com.mobile.jobsearchapplication.ui.features.profile.ProfileViewModel
import com.mobile.jobsearchapplication.ui.features.profile.SectionCVProfile
import com.mobile.jobsearchapplication.ui.features.profile.SectionInfoProfile
import com.mobile.jobsearchapplication.ui.features.profile.SectionUpdatedProfile

@Composable
fun JobPostManagementScreen(
    navController: NavController,
    query: String,
    jPMViewMode: JobPostManagementViewModel = viewModel()
) {
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
            TabsMenuJobPostManagement(jPMViewMode, query)
        }
    }
}

@Composable
fun TabsMenuJobPostManagement(
    jPMViewMode: JobPostManagementViewModel,
    query: String
) {
    val tabs = listOf("Bài đăng", "Người ứng tuyển")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
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
                    Text("asdasdasdasd")
                }
            }
        }
    }
}