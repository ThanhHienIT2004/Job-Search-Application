package com.mobile.jobsearchapplication.ui.components.menuBar.saved

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuBarSavedModel(val icon: ImageVector,val route: String ,val title: String) {
    data object Applied: MenuBarSavedModel(icon = Icons.Filled.Approval, "applied_screen", "Bài đã ứng tuyển")
    data object Posted: MenuBarSavedModel(icon = Icons.Filled.PostAdd, "posted_screen", "Bài đã đăng")
    data object Saved: MenuBarSavedModel(icon = Icons.Filled.Favorite, "favorite_screen", "Bài đã thích")

}

val listMenuBarSaved = listOf(
    MenuBarSavedModel.Applied,
    MenuBarSavedModel.Posted,
    MenuBarSavedModel.Saved
)