package com.mobile.jobsearchapplication.ui.features.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileTextState(
    val fullName: String = "",
    val age: String = "",
    val gender: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val school: String = "",
    val major: String = "",
    val gpa: String = "",
    val skills: String = "",
    val languages: String = "",
    val company: String = "",
    val position: String = "",
    val time: String = "",
    val projects: String = "",
    val certification: String = "",
    val timeCertification: String = ""
)

data class ProfileState(
    var isEditProfile: Boolean = false,
    val onIconEditProfile: Boolean = false,
    val onIconChangedProfile: Boolean = false
)

class ProfileViewModel: ViewModel() {
    private val _profileTextState = MutableStateFlow(ProfileTextState())
    val profileTextState = _profileTextState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    fun onIconEditProfile(onIconEditProfile: Boolean) {
        _profileState.value = _profileState.value.copy(
            onIconEditProfile = !onIconEditProfile
        )
    }
}