package com.mobile.jobsearchapplication.ui.features.job

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.ui.features.jobCategory.JobCategoryUiState
import com.mobile.jobsearchapplication.ui.features.jobCategory.JobCategoryViewModel


@Composable
fun SectionListJob(
    jobVM: JobViewModel,
    jobCategoryVM: JobCategoryViewModel,
    navController: NavController
) {
    val jobsState by jobVM.uiState.collectAsState()
    val jobCategoryUiState by jobCategoryVM.uiState.collectAsState()

    when (jobsState) {
        is JobUiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
            )
        }
        is JobUiState.Success -> {
            val categories = (jobCategoryUiState as JobCategoryUiState.Success).jobCategories
            val jobsByCategory = (jobsState as JobUiState.Success).jobByCategory

            categories.forEachIndexed { index, category ->
                Text(
                    text = category.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp, top = 20.dp)
                )

                RecommendedJobsList(
                    jobs = jobsByCategory[index],
                    favoriteIcons = (jobsState as JobUiState.Success).favoriteIcons,
                    navController = navController
                )
            }
        }
        is JobUiState.Error -> {
            Text(
                text = (jobsState as JobUiState.Error).message,
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun RecommendedJobsList(
    jobs: List<Job>,
    favoriteIcons: List<FavoriteIcon>,
    navController: NavController,
    modifierItem: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        if (jobs.isEmpty()) {
            Text(
                text = "Không có công việc phù hợp",
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp),
                color = Color.Gray
            )
        } else {
            val pagerState = rememberPagerState(pageCount = { jobs.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 40.dp),
                pageSpacing = 16.dp
            ) { page ->
                JobItem(
                    job = jobs[page],
                    favoriteIcon = favoriteIcons[page],
                    onClick = {
                        navController.navigate("job_detail_screen/${jobs[page].id}")
                    },
                    modifierItem = modifierItem
                )
            }
        }
    }
}


@Composable
fun JobItem(
    job: Job,
    favoriteIcon: FavoriteIcon,
    onClick: () -> Unit,
    modifierItem: Modifier = Modifier
) {
    Card(
        modifier = modifierItem
            .fillMaxWidth()
            .height(250.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFCFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                maxLines = 2, color = Color(0xFF414949),
                fontSize = 14.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp, 8.dp)
                    .fillMaxWidth()
            )
            Row {
                Column(
                    modifier = Modifier.weight(5f)
                ) {
                    Text(
                        text = job.location,
                        fontSize = 12.sp, color = Color(0xFF414949),
                        modifier = Modifier.padding(8.dp, 4.dp)
                    )
                    Text(
                        text = if (job.salaryMin != null && job.salaryMax != null) {
                            "${job.salaryMin} - ${job.salaryMax} ${job.currency}"
                        } else {
                            "Salary not specified"
                        },
                        color = Color(0xFF414949),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Icon Favorite",
                    modifier = Modifier
                        .weight(1f)
                        .size(32.dp)
                        .clickable {
                            favoriteIcon.onClick
                        },
                    tint = if (favoriteIcon.isCheck) Color.Red else Color.Gray,
                )
            }
        }
    }
}