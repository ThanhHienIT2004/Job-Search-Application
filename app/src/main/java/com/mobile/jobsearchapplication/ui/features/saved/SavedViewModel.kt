package com.mobile.jobsearchapplication.ui.features.saved

import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.repository.job.JobRepository
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class SavedUiState{
    data object Loading: SavedUiState()

    data class Success(
        val appliedJobs: List<Job>? = emptyList(),
        val postedJobs: List<Job>? = emptyList(),
        val favoriteJobs: List<Job>? = emptyList()
    ): SavedUiState()

    data class Error(val message: String): SavedUiState()
}

class SavedViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow<SavedUiState>(SavedUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _currentTab = MutableStateFlow<String>("")
    val currentTab = _currentTab.asStateFlow()

    private val jobRepository = JobRepository()

    fun onTabChanged(tab: String) {
        _currentTab.value = tab
    }

    fun loadPostedJobs() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    jobRepository.getPostedJobs(getLoggedInUserId())
                }

                _uiState.value = when (val currentState = _uiState.value) {
                    is SavedUiState.Success -> currentState.copy(postedJobs = response.data)
                    else -> SavedUiState.Success(postedJobs = response.data)
                }

            } catch (e: Exception) {
                _uiState.value = SavedUiState.Error("Error fetching posted: ${e.message}")
            }
        }
    }

    fun loadFavoriteJobs() {
        viewModelScope.launch {
            try {
                val favoriteJobs = withContext(Dispatchers.IO) {
                    jobRepository.getFavoriteJobs(getLoggedInUserId()).data
                }

                _uiState.value = when (val current = _uiState.value) {
                    is SavedUiState.Success -> current.copy(favoriteJobs = favoriteJobs)
                    else -> SavedUiState.Success(favoriteJobs = favoriteJobs)
                }
            } catch (e: Exception) {
                _uiState.value = SavedUiState.Error("Error fetching favorite jobs: ${e.message}")
            }
        }
    }

    fun loadAppliedJobs() {
        viewModelScope.launch {
            try {
                val favoriteJobs = withContext(Dispatchers.IO) {
                    jobRepository.getAppliedJobs(getLoggedInUserId()).data
                }

                _uiState.value = when (val current = _uiState.value) {
                    is SavedUiState.Success -> current.copy(appliedJobs = favoriteJobs)
                    else -> SavedUiState.Success(appliedJobs = favoriteJobs)
                }
            } catch (e: Exception) {
                _uiState.value = SavedUiState.Error("Error fetching favorite jobs: ${e.message}")
            }
        }
    }
}