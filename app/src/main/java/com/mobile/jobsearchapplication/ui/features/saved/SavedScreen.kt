package com.mobile.jobsearchapplication.ui.features.saved

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.job.AppliedJobs
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.dropdownMenu.MenuGrid
import com.mobile.jobsearchapplication.ui.components.emptyState.EmptyState
import com.mobile.jobsearchapplication.ui.components.menuBar.saved.MenuBarSaved
import com.mobile.jobsearchapplication.ui.components.skeleton.JobItemSkeleton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar
import com.mobile.jobsearchapplication.ui.features.job.JobItem
import com.mobile.jobsearchapplication.ui.features.job.JobUiState
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.ui.navigation.NavigationRoute.Companion.baseNavController
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn


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
        savedVM.loadAppliedJobs()
        jobVM.loadFavoriteJobs()
    }

    BaseScreen(
        actionsTop = {
            TitleTopBar(
                modifier = Modifier.padding(start = 32.dp),
                text = "Bài đăng của bạn"
            )
        },
        actionsBot = { BottomNavBar(navController) }
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
                            "applied_screen" -> {
                                savedVM.loadAppliedJobs()
                                jobVM.loadFavoriteJobs()
                            }
                            "posted_screen" -> {
                                savedVM.loadPostedJobs()
                                jobVM.loadFavoriteJobs()
                            }
                            "favorite_screen" -> {
                                savedVM.loadFavoriteJobs()
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
                    JobItemSkeleton()
                }
                jobUiState.value is JobUiState.Error -> { (jobUiState.value as JobUiState.Error).message }
                savedUiState.value is SavedUiState.Error -> { (savedUiState.value as SavedUiState.Error).message }
                !isUserLoggedIn() -> { // kiểm tra trạng thái đăng nhập
                    EmptyState(
                        icon = R.drawable.img_go_log_in,
                        message = "Hãy đăng nhập để tiếp tục",
                        onClick = { baseNavController(navController, "auth_screen") }
                    )
                }
                savedUiState.value is SavedUiState.Success && jobUiState.value is JobUiState.Success -> {
                    val listItem = when(tabSaved.value) {
                        "posted_screen" -> (savedUiState.value as SavedUiState.Success).postedJobs ?: emptyList()
                        "favorite_screen" -> (savedUiState.value as SavedUiState.Success).favoriteJobs ?: emptyList()
                        else -> emptyList()
                    }
                    val listAppliedJobs = (savedUiState.value as SavedUiState.Success).appliedJobs ?: emptyList()
                    val checkListEmpty = when(tabSaved.value) {
                        "applied_screen" -> listAppliedJobs.isEmpty()
                        "posted_screen" -> listItem.isEmpty()
                        "favorite_screen" -> listItem.isEmpty()
                        else -> false
                    }
                    if (checkListEmpty) {
                        EmptyState(
                            icon = R.drawable.img_empty_state,
                            message = when(tabSaved.value) {
                                "applied_screen" -> "Hãy ứng tuyển cho công việc nào đó"
                                "posted_screen" -> "Hãy đăng tuyển cho công việc nào đó"
                                "favorite_screen" -> "Hãy thích công việc nào đó"
                                else -> ""
                            },
                            onClick = {
                                baseNavController(navController, route = when (tabSaved.value) {
                                    "applied_screen" -> "home_screen"
                                    "posted_screen" -> "post_screen"
                                    "favorite_screen" -> "home_screen"
                                    else -> ""
                                })
                            }
                        )
                    } else {
                        SectionListSaved(
                            jobVM = jobVM,
                            jobs =  listItem,
                            appliedJobs = listAppliedJobs,
                            tag = tabSaved.value,
                            navController = navController,
                            columnCount = columnCount,
                            modifier = Modifier
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
    appliedJobs: List<AppliedJobs>,
    jobVM: JobViewModel,
    tag: String = "posted_screen",
    navController: NavController,
    columnCount: Int = 2,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        if (tag == "applied_screen") {
            items(appliedJobs.size) { job ->
                AppliedJobItem(
                    jobVM = jobVM,
                    job = appliedJobs[job],
                    onClick = {
                        baseNavController(navController,"job_detail_screen/${appliedJobs[job].id}")
                    },
                    isOneColumn = columnCount == 1,
                    modifier = Modifier.fillMaxWidth()
                        .then(
                            if (columnCount == 1) Modifier.aspectRatio(1.8f)
                            else Modifier.aspectRatio(1f)
                        )
                )
            }
        } else {
            items(jobs.size) { job ->
                    JobItem(
                        jobVM = jobVM,
                        job = jobs[job],
                        onClick = {
                            if (tag == "posted_screen")
                                baseNavController(navController,"job_post_management/${jobs[job].id}")
                            else if (tag == "favorite_screen")
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
}

@Composable
fun AppliedJobItem(
    jobVM: JobViewModel,
    job: AppliedJobs,
    onClick: () -> Unit,
    isOneColumn: Boolean = true,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFCFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = job.jobImage,
                contentDescription = "Job Image",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(0.95f)
                    .then(
                        if (isOneColumn) {
                            Modifier.fillMaxHeight(0.6f)
                        } else {
                            Modifier.fillMaxHeight(0.4f)
                        }
                    ),
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.error)
            )
            Text(
                text = job.title, textAlign = TextAlign.Start,
                maxLines = 1, color = Color(0xFF414949),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp)
            )
            Row {
                Column(
                    modifier = Modifier.weight(5f)
                ) {
                    Text(
                        text = "Trạng thái: ${job.statusResponse}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                    Text(
                        text = "Nơi làm việc: ${job.location}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                    Text(
                        text = "Mức lương: ${if (job.salaryMin != null && job.salaryMax != null) {
                            "${job.salaryMin} - ${job.salaryMax} ${job.currency}"}
                        else {
                            "Salary not specified"
                        }}",
                        color = Color(0xFF414949),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 1.dp)
                    )
                }
            }
        }
    }
}