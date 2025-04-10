package com.mobile.jobsearchapplication.ui.components.menuBar.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MenuBarProfileState (
    val onMainMenuBar: MenuBarProfile = MenuBarProfile.Information
)

class MenuBarProfileViewModel: ViewModel() {
    private val _menuBarState = MutableStateFlow(MenuBarProfileState())
    val menuBarState = _menuBarState.asStateFlow()

    val listMainMenuBarProfile = listOf(
        MenuBarProfile.Information,
        MenuBarProfile.Education,
        MenuBarProfile.Experience,
        MenuBarProfile.Certifications
    )

    private val listMenuItemInformation = listOf(
        MenuItemInformation.FullName,
        MenuItemInformation.Age,
        MenuItemInformation.Gender,
        MenuItemInformation.Address,
        MenuItemInformation.PhoneNumber,
        MenuItemInformation.Email
    )
    private val listMenuItemEducation = listOf(
        MenuItemEducation.School,
        MenuItemEducation.Major,
        MenuItemEducation.GPA,
        MenuItemEducation.Skills,
        MenuItemEducation.Languages
    )
    private val listMenuItemExperience = listOf(
        MenuItemExperience.Company,
        MenuItemExperience.Position,
        MenuItemExperience.Time,
        MenuItemExperience.Projects
    )
    private val listMenuItemCertifications = listOf(
        MenuItemCertifications.Certification,
        MenuItemCertifications.Time
    )

    fun onMainMenuBar(menuBarProfile: MenuBarProfile) {
        _menuBarState.value = _menuBarState.value.copy(
            onMainMenuBar = menuBarProfile
        )
    }

    fun getListMenuItem(menuProfile: MenuBarProfile): List<ContentMenuItemProfile> {
        return when (menuProfile) {
            MenuBarProfile.Information -> listMenuItemInformation
            MenuBarProfile.Education -> listMenuItemEducation
            MenuBarProfile.Experience -> listMenuItemExperience
            MenuBarProfile.Certifications -> listMenuItemCertifications
        }
    }
}