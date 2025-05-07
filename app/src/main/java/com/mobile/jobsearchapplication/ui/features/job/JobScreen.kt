package com.mobile.jobsearchapplication.ui.features.job

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.ui.components.skeleton.SectionJobSkeleton
import com.mobile.jobsearchapplication.ui.features.jobCategory.JobCategoryUiState
import com.mobile.jobsearchapplication.ui.features.jobCategory.JobCategoryViewModel
import com.mobile.jobsearchapplication.ui.features.jobDetail.toVietnamesePeriod
import com.mobile.jobsearchapplication.ui.navigation.NavigationRoute.Companion.baseNavController
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn

@Composable
fun SectionListJob(
    jobVM: JobViewModel,
    jobCategoryVM: JobCategoryViewModel,
    navController: NavController
) {
    val jobsState by jobVM.uiState.collectAsState()
    val jobCategoryUiState by jobCategoryVM.uiState.collectAsState()

    when {
        jobsState is JobUiState.Loading || jobCategoryUiState is JobCategoryUiState.Loading -> {
            SectionJobSkeleton()
        }
        jobsState is JobUiState.Success && jobCategoryUiState is JobCategoryUiState.Success -> {
            val categories = (jobCategoryUiState as JobCategoryUiState.Success).jobCategories
            val jobsByCategory = (jobsState as JobUiState.Success).jobByCategory

            categories.forEachIndexed { index, category ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Text(
                        text = "Mở rộng",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(end = 16.dp)
                            .clickable { baseNavController(navController, "filter_screen/${category.name}") }
                    )
                }

                jobsByCategory?.let {
                    RecommendedJobsList(
                        jobVM,
                        jobs = it[index],
                        navController = navController
                    )
                }
            }
        }
        jobsState is JobUiState.Error -> {
            Text(
                text = (jobsState as JobUiState.Error).message,
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
        jobCategoryUiState is JobCategoryUiState.Error -> {
            Text(
                text = (jobCategoryUiState as JobCategoryUiState.Error).message,
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun RecommendedJobsList(
    jobVM: JobViewModel,
    jobs: List<Job>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (jobs.isEmpty()) {
            Text(
                text = "Không có công việc phù hợp",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray
            )
        } else {
            val pagerState = rememberPagerState(pageCount = { jobs.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 24.dp),
                beyondViewportPageCount = 2,
                pageSpacing = 12.dp,
            ) { page ->
                JobItem(
                    jobVM,
                    job = jobs[page],
                    onClick = { navController.navigate("job_detail_screen/${jobs[page].id}") },
                    modifier = modifier.padding(bottom = 20.dp)
                )
            }
        }
    }
}


@Composable
fun JobItem(
    jobVM: JobViewModel,
    job: Job,
    onClick: () -> Unit,
    isEnableIcon: Boolean = true,
    modifier: Modifier = Modifier
) {
    val jobUiState by jobVM.uiState.collectAsState()
    var isFavorite = rememberSaveable(jobUiState, job.id) {
        (jobUiState as JobUiState.Success).favoriteJobs?.contains(job.id) == true
    }

    val context = LocalContext.current

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
                    .fillMaxHeight(0.6f),
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
                        text = "Nơi làm việc: ${job.location}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                    Text(
                        text = "Mức lương: ${if (job.salaryMin != null && job.salaryMax != null) {
                            "${job.salaryMin} - ${job.salaryMax} ${job.currency} ${job.salaryPeriod.toVietnamesePeriod()}"}
                        else {
                            "Salary not specified"
                        }}",
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 1.dp)
                    )
                }
                if (isEnableIcon) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Icon Favorite",
                        modifier = Modifier.weight(1f).size(32.dp)
                            .clickable {
                                if (!isUserLoggedIn()) {
                                    Toast.makeText(context, "Vui lòng đăng nhập để sử dụng", Toast.LENGTH_SHORT).show()
                                } else {
                                    isFavorite = !isFavorite
                                    jobVM.updateFavoriteApi(jobId = job.id.toString(), state = isFavorite)
                                }
                            },
                        tint = if (isFavorite) Color.Red else Color.Gray,
                    )
                }
            }
        }
    }
}