package com.mobile.jobsearchapplication.ui.features.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class HomeUiState {
    data object Loading: HomeUiState()
}

class HomeViewModel : ViewModel() {
}