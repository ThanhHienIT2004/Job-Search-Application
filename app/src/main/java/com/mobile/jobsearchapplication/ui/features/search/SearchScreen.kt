package com.mobile.jobsearchapplication.ui.features.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val searchHistory = listOf("1", "2", "3")

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        if (!active) {
            // Khi không active: Hiển thị mũi tên và DockedSearchBar trong Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                DockedSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { newQuery ->
                        println("Search: $newQuery")
                    },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        Row {
                            IconButton(onClick = { /* TODO: Xử lý sự kiện mic */ }) {
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
                    searchHistory.takeLast(3).forEach { item ->
                        ListItem(
                            modifier = Modifier.clickable { query = item },
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
            // Khi active: Chỉ hiển thị DockedSearchBar chiếm toàn bộ chiều rộng
            DockedSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { newQuery ->
                    println("Search: $newQuery")
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    Row {
                        IconButton(onClick = { /* TODO: Xử lý sự kiện mic */ }) {
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
                modifier = Modifier.fillMaxWidth() // Chiếm toàn bộ chiều rộng
            ) {
                searchHistory.takeLast(3).forEach { item ->
                    ListItem(
                        modifier = Modifier.clickable { query = item },
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
    }
}
