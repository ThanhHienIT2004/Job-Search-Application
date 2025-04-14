package com.mobile.jobsearchapplication.ui.features.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val isJobCategoryExpanded: Boolean = false,
    val isCheckedIconJob: Boolean = false,
    val selectedJobCategory: String? = null
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun toggleJobCategoryExpansion(isExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(isJobCategoryExpanded = isExpanded)
    }

    fun toggleJobIcon(isChecked: Boolean, selectedCategory: String? = null) {
        _uiState.value = _uiState.value.copy(
            isCheckedIconJob = isChecked,
            selectedJobCategory = if (isChecked) selectedCategory else null
        )
    }
}