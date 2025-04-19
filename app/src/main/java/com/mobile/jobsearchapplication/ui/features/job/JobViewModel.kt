package com.mobile.jobsearchapplication.ui.features.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.model.user.FavoriteJobPosting
import com.mobile.jobsearchapplication.data.repository.job.JobRepository
import com.mobile.jobsearchapplication.data.repository.jobCategory.JobCategoryRepository
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.UUID

sealed class JobUiState {
    data object Loading : JobUiState()

    data class Success(
        val jobs: List<Job>? = emptyList(),
        val jobByCategory: List<List<Job>>? = emptyList(),
        val favoriteJobs: List<UUID>? = emptyList(),
        val page: Int = 1
    ) : JobUiState()

    data class Error(val message: String) : JobUiState()
}

class JobViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<JobUiState>(JobUiState.Loading)
    val uiState: StateFlow<JobUiState> = _uiState

    private val jobCategoryRepository = JobCategoryRepository()
    private val jobRepository = JobRepository()
    private val userRepository = UserRepository()


    fun checkIsFavorite(jobId: UUID): Boolean {
        return when (val state = _uiState.value) {
            is JobUiState.Success -> state.favoriteJobs?.contains(jobId) ?: false
            else -> false
        }
    }

    fun loadJobByCategory() {
        viewModelScope.launch {
            try {
                val jobsByCategory = withContext(Dispatchers.IO) {
                    val page = when(val current = _uiState.value) {
                        is JobUiState.Success -> current.page
                        else -> 1
                    }

                    val categories = jobCategoryRepository.getAllJobCategories(
                        page.toString(), "20"
                    ).data?.data ?: emptyList()
                    categories.mapIndexed() { index, category ->
                        if (index >5) return@mapIndexed emptyList()
                        jobRepository.getJobsByCategory(category.id).data
                    }
                }

                _uiState.value = when (val currentState = _uiState.value) {
                    is JobUiState.Success -> currentState.copy(jobByCategory = jobsByCategory)
                    else -> JobUiState.Success(jobByCategory = jobsByCategory)
                }

                loadFavoriteJobs()
            } catch (e: Exception) {
                _uiState.value = JobUiState.Error(message = "Lỗi khi fetch jobs by category: ${e.message}")
            }
        }
    }

    fun loadFavoriteJobs() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    userRepository.getInfo(getLoggedInUserId())
                        .data?.favoritePosts
                            ?.split(",")
                            ?.map { it.trim() }
                            ?.filter { it.isNotBlank() }
                            ?.map { UUID.fromString(it) }
                }
                _uiState.value = when (val currentState = _uiState.value) {
                    is JobUiState.Success -> currentState.copy(favoriteJobs = response)
                    else -> JobUiState.Success(favoriteJobs = response)
                }
            } catch (e: Exception) {
                _uiState.value = JobUiState.Error("Error fetching favorite jobs: ${e.message}")
            }
        }
    }

    fun updateFavoriteApi(jobId: UUID, state: Boolean) {
        viewModelScope.launch {
            val userId = getLoggedInUserId()
            val request = FavoriteJobPosting(jobId, state)
            try {
                withContext(Dispatchers.IO) {
                    userRepository.favoriteJobPosting(uuid = userId, request = request)
                }
                Timer("Updated favorite: $jobId, state: $state")
                loadFavoriteJobs()

            } catch (e: Exception) {
                _uiState.value = JobUiState.Error("Error updating favorite: ${e.message}")
            }
        }
    }
}