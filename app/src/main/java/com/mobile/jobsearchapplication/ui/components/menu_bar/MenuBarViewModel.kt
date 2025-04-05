package com.mobile.jobsearchapplication.ui.components.menu_bar

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MenuBarState (
    val onMainMenuBar: MenuBarUser = MenuBarUser.Profile
)

class MenuBarViewModel: ViewModel() {
    private val _menuBarState = MutableStateFlow(MenuBarState())
    val menuBarState = _menuBarState.asStateFlow()

    val listMainMenuBarUser = listOf(MenuBarUser.Profile, MenuBarUser.Posts, MenuBarUser.Settings, MenuBarUser.Logout)

    private val listItemMenuProfile = listOf(MenuItemProfile.PersonalInfo, MenuItemProfile.UpdateInfo, MenuItemProfile.ChangedBusinessAccount)

    private val listItemMenuPosts = listOf(MenuItemPosts.PublishedPosts, MenuItemPosts.Favorites, MenuItemPosts.AppliedJobs)

    private val listItemForSettings = listOf(MenuItemSettings.DarkMode, MenuItemSettings.Notifications)

    private val listItemForLogout = listOf(MenuItemLogout.DisableAccount, MenuItemLogout.Logout)

    fun onMainMenuBar(menuBarUser: MenuBarUser) {
        _menuBarState.value = _menuBarState.value.copy(onMainMenuBar = menuBarUser)
    }

    fun getItemMenu(menuBarUser: MenuBarUser): List<MenuItemUser> {
        return when(menuBarUser) {
            MenuBarUser.Profile -> listItemMenuProfile
            MenuBarUser.Posts -> listItemMenuPosts
            MenuBarUser.Settings -> listItemForSettings
            MenuBarUser.Logout -> listItemForLogout
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}