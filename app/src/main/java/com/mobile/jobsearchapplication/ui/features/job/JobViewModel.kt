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


data class FavoriteIcon(
    var isCheck: Boolean = false,
    val onClick: () -> Unit = {  }
)

sealed class JobUiState {
    data object Loading : JobUiState()

    data class Success(
        val jobs: List<Job> = emptyList(),
        val favoriteIcons: List<FavoriteIcon> = emptyList(),
        val jobByCategory: List<List<Job>> = emptyList(),
        val favoriteIconsCate: List<List<FavoriteIcon>> = emptyList(),
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
        loadJobsAndFavorites()
        loadJobByCategory()
    }

    private fun loadJobsAndFavorites() {
        viewModelScope.launch {
            _uiState.value = JobUiState.Loading
            try {
                val (jobResponse, favoritePosts) = withContext(Dispatchers.IO) {
                    val jobs = RetrofitClient.jobApiService.getJobs()
                    val favorites = loadFavoritePosts()
                    jobs to favorites
                }

                if (jobResponse.message == "Success" && jobResponse.data != null) {
                    val favoriteIcons = jobResponse.data.data.map { job ->
                        FavoriteIcon(
                            isCheck = job.id in favoritePosts,
                            onClick = { updateFavoriteApi(job.id, job.id in favoritePosts) }
                        )
                    }
                    _uiState.value = JobUiState.Success(
                        jobs = jobResponse.data.data,
                        favoriteIcons = favoriteIcons,
                        pageCount = jobResponse.data.pageCount
                    )
                } else {
                    _uiState.value = JobUiState.Error(jobResponse.message)
                }
            } catch (e: Exception) {
                _uiState.value = JobUiState.Error("Error fetching jobs: ${e.message}")
            }
        }
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

    private suspend fun loadFavoritePosts(): List<UUID> {
        return try {
            val response = userRepository.getInfo(getLoggedInUserId())
            if (response.isSuccess && response.data?.favoritePosts != null) {
                response.data.favoritePosts
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                    .map { UUID.fromString(it) }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList() // Trả về rỗng nếu lỗi
        }
    }

    private fun updateFavoriteApi(jobId: UUID, state: Boolean) {
        viewModelScope.launch {
            val userId = getLoggedInUserId()
            val request = FavoriteJobPosting(jobId, state)
            try {
                withContext(Dispatchers.IO) {
                    userRepository.favoriteJobPosting(uuid = userId, request = request)
                }
                loadJobsAndFavorites()
            } catch (e: Exception) {
                _uiState.value = JobUiState.Error("Error updating favorite: ${e.message}")
            }
        }
    }
}