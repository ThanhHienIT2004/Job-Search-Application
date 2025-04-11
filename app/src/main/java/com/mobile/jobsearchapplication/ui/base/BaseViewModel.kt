package com.mobile.jobsearchapplication.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class BaseState(
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

open class BaseViewModel : ViewModel() {
    private val _baseState = MutableStateFlow(BaseState())
    val baseState = _baseState.asStateFlow()

    protected fun showLoading() {
        _baseState.value = _baseState.value.copy(isLoading = true)
    }

    protected fun hideLoading() {
        _baseState.value = _baseState.value.copy(isLoading = false)
    }

    protected fun showErrorMessage(message: String){
        _baseState.value = _baseState.value.copy(errorMessage = message)
    }
}