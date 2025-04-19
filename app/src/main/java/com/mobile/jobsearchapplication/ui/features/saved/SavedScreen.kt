package com.mobile.jobsearchapplication.ui.features.saved

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.MenuNavBar
import com.mobile.jobsearchapplication.ui.components.menuBar.saved.MenuBarSaved
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar


@Composable
fun SavedScreen(
    navController: NavController,
    savedVM: SavedViewModel = viewModel()
) {
    val savedUiState = savedVM.uiState.collectAsState()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "favorite_screen"

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "favorite_screen" -> savedVM.loadFavoriteJobs()
            "posted_screen" -> savedVM.loadPostedJobs()
        }
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
        LazyColumn(
            modifier = Modifier.padding(padding),
        ) {
            item {
                MenuBarSaved(navController, {  })

                when(savedUiState.value) {
                    is SavedUiState.Loading -> {}
                    is SavedUiState.Error -> {}
                    else -> {
                        val list = when (currentRoute) {
                            "favorite_screen" -> (savedUiState.value as SavedUiState.Success).favoriteJobs
                            "posted_screen" -> (savedUiState.value as SavedUiState.Success).postedJobs
                            else -> emptyList()
                        }

                        list?.forEach { item ->
                            Text(item.title)
                        }
                    }
                }
            }
        }
    }
}