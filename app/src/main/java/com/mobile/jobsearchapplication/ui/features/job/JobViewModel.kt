package com.mobile.jobsearchapplication.ui.features.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.model.user.FavoriteJobPosting
import com.mobile.jobsearchapplication.data.repository.job.JobRepository
import com.mobile.jobsearchapplication.data.repository.jobCategory.JobCategoryRepository
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import com.mobile.jobsearchapplication.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

sealed class JobUiState {
    data object Loading : JobUiState()

    data class Success(
        val jobs: List<Job>? = emptyList(),
        val jobByCategory: List<List<Job>>? = emptyList(),
        val favoriteJobs: List<UUID>? = emptyList(),
        val pageCount: Int = 1
    ) : JobUiState()

    data class Error(val message: String) : JobUiState()
}

class JobViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<JobUiState>(JobUiState.Loading)
    val uiState: StateFlow<JobUiState> = _uiState

    private val jobCategoryRepository = JobCategoryRepository()
    private val jobRepository = JobRepository()
    private val userRepository = UserRepository()

    init {
        loadJobByCategory()
        loadFavoriteJobs()
    }

    fun checkIsFavorite(jobId: UUID): Boolean {
        return (_uiState.value as JobUiState.Success).favoriteJobs?.contains(jobId) ?: false
    }

    private fun loadJobByCategory() {
        viewModelScope.launch {
            try {
                val jobsByCategory = withContext(Dispatchers.IO) {
                    val categories = jobCategoryRepository.getAllJobCategories("1", "20").data?.data ?: emptyList()
                    categories.map { category ->
                        jobRepository.getJobsByCategory(category.id).data
                    }
                }

                _uiState.value = when (val currentState = _uiState.value) {
                    is JobUiState.Success -> currentState.copy(jobByCategory = jobsByCategory)
                    else -> JobUiState.Success(jobByCategory = jobsByCategory)
                }

            } catch (e: Exception) {
                _uiState.value = JobUiState.Error(message = "Lỗi khi fetch jobs by category: ${e.message}")
            }
        }
    }

    private fun loadFavoriteJobs() {
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

                loadFavoriteJobs()
            } catch (e: Exception) {
                _uiState.value = JobUiState.Error("Error updating favorite: ${e.message}")
            }
        }
    }
}