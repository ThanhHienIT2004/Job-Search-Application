package com.mobile.jobsearchapplication.ui.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

class SearchViewModel(private val dataStore: DataStore<Preferences>) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    init {
        loadSearchHistory()
    }

    fun searchJobs(query: String, userId: String? = null, isFullSearch: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val jobs = withContext(Dispatchers.IO) {
                    val response = RetrofitClient.jobApiService.getJobs()
                    if (response.message == "Success" && response.data != null) {
                        response.data.data
                    } else {
                        emptyList()
                    }
                }

                // Tách query thành các từ khóa
                val keywords = query.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }

                // Lọc công việc
                val filteredJobs = jobs.filter { job ->
                    val matchesUserId = userId == null || job.postedBy.toString() == userId
                    val matchesQuery = if (keywords.isEmpty()) {
                        true // Nếu không có từ khóa, trả về tất cả
                    } else if (isFullSearch) {
                        // Tìm kiếm đầy đủ: khớp tất cả từ khóa
                        keywords.all { keyword ->
                            job.title.contains(keyword, ignoreCase = true) ?: false
                        }
                    } else {
                        // Tìm kiếm tức thời: khớp với query nguyên văn
                        job.title.contains(query.trim(), ignoreCase = true) ?: false
                    }
                    matchesUserId && matchesQuery
                }

                // Sắp xếp theo createdAt từ mới đến cũ
                val sortedJobs = filteredJobs.sortedByDescending { it.createdAt ?: "" }

                _uiState.value = SearchUiState.Success(sortedJobs)
                if (query.isNotEmpty() && isFullSearch) {
                    saveSearchQuery(query)
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error("Lỗi khi tìm kiếm công việc: ${e.message}")
            }
        }
    }

    private fun saveSearchQuery(query: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val currentHistory = preferences[SEARCH_HISTORY_KEY]?.toMutableSet() ?: mutableSetOf()
                currentHistory.add(query)
                if (currentHistory.size > 5) {
                    currentHistory.remove(currentHistory.first())
                }
                preferences[SEARCH_HISTORY_KEY] = currentHistory
            }
            loadSearchHistory()
        }
    }

    private fun loadSearchHistory() {
        viewModelScope.launch {
            val history = dataStore.data
                .map { preferences ->
                    preferences[SEARCH_HISTORY_KEY]?.toList()?.reversed() ?: emptyList()
                }
                .first()
            _searchHistory.value = history
        }
    }

    companion object {
        private val SEARCH_HISTORY_KEY = stringSetPreferencesKey("search_history")
    }
}

sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data class Success(val jobs: List<Job>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}