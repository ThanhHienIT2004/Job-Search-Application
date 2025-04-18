package com.mobile.jobsearchapplication.ui.features.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.features.job.JobItem
import com.mobile.jobsearchapplication.utils.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, userId: String? = null) {
    val context = LocalContext.current
    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(context.dataStore)
    )
    var query by rememberSaveable { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        // Thanh tìm kiếm và icon quay lại
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon Quay lại (ẩn khi SearchBar active)
                AnimatedVisibility(
                    visible = !active,
                    enter = fadeIn(animationSpec = tween(300)) +
                            scaleIn(animationSpec = tween(300), initialScale = 0.8f) +
                            slideInHorizontally(animationSpec = tween(300), initialOffsetX = { -20 }),
                    exit = fadeOut(animationSpec = tween(300)) +
                            scaleOut(animationSpec = tween(300), targetScale = 0.8f) +
                            slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { -20 })
                ){
                    Row {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Quay lại",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                // Thanh tìm kiếm
                DockedSearchBar(
                    query = query,
                    onQueryChange = { newQuery ->
                        query = newQuery
                        coroutineScope.launch {
                            delay(300) // Debounce 300ms
                            viewModel.searchJobs(newQuery, userId, isFullSearch = false)
                        }
                    },
                    onSearch = { newQuery ->
                        viewModel.searchJobs(newQuery, userId, isFullSearch = true)
                        active = false
                    },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(text = "Tìm kiếm công việc") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Tìm kiếm",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingIcon = {
                        Row {
                            if (active) {
                                TextButton(onClick = { active = false; query = "" }) {
                                    Text(
                                        text = "Hủy",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            } else {
                                IconButton(onClick = { /* TODO: Xử lý microphone */ }) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_mic),
                                        contentDescription = "Microphone",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                if (query.isNotEmpty()) {
                                    IconButton(onClick = { query = "" }) {
                                        Icon(
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = "Xóa",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = if (active) 4.dp else 8.dp)
                ) {
                    LazyColumn {
                        items(searchHistory) { item ->
                            ListItem(
                                modifier = Modifier.clickable {
                                    query = item
                                    viewModel.searchJobs(item, userId, isFullSearch = true)
                                    active = false
                                },
                                headlineContent = { Text(text = item) },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_history),
                                        contentDescription = "Lịch sử",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // Kết quả tìm kiếm
        when (uiState) {
            is SearchUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is SearchUiState.Success -> {
                val jobs = (uiState as SearchUiState.Success).jobs

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (jobs.isNotEmpty()) {
                        item {
                            Text(
                                text = "Kết quả tìm kiếm",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                            )
                        }
                        items(
                            items = jobs,
                            key = { job -> job.id }
                        ) { job ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            ) {
                                JobItem(
                                    job = job,
                                    onClick = {
                                        job.id.let {
                                            navController.navigate("job_detail_screen/${job.id}") {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    inclusive = false
                                                }
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    } else if (query.isNotEmpty()) {
                        item {
                            EmptySearchCard()
                        }
                    }
                }
            }
            is SearchUiState.Error -> {
                val message = (uiState as SearchUiState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            is SearchUiState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nhập từ khóa để tìm kiếm công việc",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun EmptySearchCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Không tìm thấy công việc",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Thử từ khóa khác hoặc kiểm tra lại chính tả",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}