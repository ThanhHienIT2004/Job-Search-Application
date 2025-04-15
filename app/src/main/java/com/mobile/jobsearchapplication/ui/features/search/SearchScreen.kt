package com.mobile.jobsearchapplication.ui.features.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.features.home.JobItem
import com.mobile.jobsearchapplication.utils.dataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, userId: String? = null) {
    val context = LocalContext.current
    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(context.dataStore)
    )
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        if (!active) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                DockedSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { newQuery ->
                        viewModel.searchJobs(newQuery, userId)
                        active = false
                    },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(text = "Search jobs") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        Row {
                            IconButton(onClick = { /* TODO: Handle microphone */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_mic),
                                    contentDescription = "Microphone"
                                )
                            }
                            if (active) {
                                IconButton(onClick = {
                                    if (query.isNotEmpty()) query = "" else active = false
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Close"
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    searchHistory.forEach { item ->
                        ListItem(
                            modifier = Modifier.clickable {
                                query = item
                                viewModel.searchJobs(item, userId)
                                active = false
                            },
                            headlineContent = { Text(text = item) },
                            leadingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_history),
                                    contentDescription = "History"
                                )
                            }
                        )
                    }
                }
            }
        } else {
            DockedSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { newQuery ->
                    viewModel.searchJobs(newQuery, userId)
                    active = false
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search jobs") },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    Row {
                        IconButton(onClick = { /* TODO: Handle microphone */ }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_mic),
                                contentDescription = "Microphone"
                            )
                        }
                        IconButton(onClick = {
                            if (query.isNotEmpty()) query = "" else active = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                searchHistory.forEach { item ->
                    ListItem(
                        modifier = Modifier.clickable {
                            query = item
                            viewModel.searchJobs(item, userId)
                            active = false
                        },
                        headlineContent = { Text(text = item) },
                        leadingContent = {
                            Icon(
                                painter = painterResource(R.drawable.ic_history),
                                contentDescription = "History"
                            )
                        }
                    )
                }
            }
        }

        // Display search results
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
                LazyColumn {
                    items((uiState as SearchUiState.Success).jobs.size) { index ->
                        JobItem(job = (uiState as SearchUiState.Success).jobs[index], onClick = {
                            navController.navigate("job_detail/${(uiState as SearchUiState.Success).jobs[index].id}")
                        })
                    }
                }
            }
            is SearchUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as SearchUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            is SearchUiState.Idle -> {
                // Display nothing or a prompt
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Enter a search query to find jobs")
                }
            }
        }
    }
}

@Composable
fun JobNear(){

}