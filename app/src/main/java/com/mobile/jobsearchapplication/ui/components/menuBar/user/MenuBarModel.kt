package com.mobile.jobsearchapplication.ui.components.menuBar.user

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EditNotifications
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.mobile.jobsearchapplication.ui.features.menuUser.UserViewModel

sealed class MenuBarUser(val icon: ImageVector, val title: String) {
    data object Profile: MenuBarUser(Icons.Filled.Person, "Icon Profile")
    data object Posts: MenuBarUser(Icons.Filled.ArtTrack, "Icon Posts")
    data object Settings: MenuBarUser(Icons.Filled.Settings, "Icon Settings")
    data object Logout: MenuBarUser(Icons.Filled.ExitToApp, "Icon Logout")
}

open class MenuItemUser(val icon: ImageVector, val title: String, val route: String? = null, var action: (() -> Unit)? = {})

sealed class MenuItemProfile(icon: ImageVector, title: String, route: String? = null, action: (() -> Unit)? = {}) : MenuItemUser(icon, title, route, action) {
    data object PersonalInfo: MenuItemProfile(Icons.Filled.PersonPin, "Hồ sơ cá nhân", "profile_screen", {})
    data object UpdateInfo: MenuItemProfile(Icons.Filled.EditNote,"Cập nhật thông tin", "update_profile_screen")
    data object ChangedBusinessAccount: MenuItemProfile(Icons.Filled.BusinessCenter, "Chuyển tài khoản kinh doanh", )
}

sealed class MenuItemPosts(icon: ImageVector, title: String, route: String? = null, action: (() -> Unit)? = {}) : MenuItemUser(icon, title, route, action) {
    data object PublishedPosts: MenuItemPosts(Icons.Filled.HistoryEdu, "Tuyển dụng đã đăng", "posted_screen")
    data object Favorites: MenuItemPosts(Icons.Filled.FavoriteBorder, "Bài đăng yêu thích", "favorite_screen")
    data object AppliedJobs: MenuItemPosts(Icons.Filled.ModeComment, "Công việc đã ứng tuyển", "applied_screen")
}

sealed class MenuItemSettings(icon: ImageVector, title: String, route: String? = null, action: (() -> Unit)? = {}) : MenuItemUser(icon, title, route, action) {
    data object DarkMode: MenuItemSettings(Icons.Filled.DarkMode, "Chế độ tối" )
    data object Notifications: MenuItemSettings(Icons.Filled.EditNotifications, "Cài đặt thông báo")
}

sealed class MenuItemLogout(icon: ImageVector, title: String, route: String? = null, action: (() -> Unit)? = {}) : MenuItemUser(icon, title, route, action) {
    data object DisableAccount: MenuItemLogout(Icons.Filled.NoAccounts, "Tạm dừng tài khoản", )
    data object Logout: MenuItemLogout(Icons.Filled.Logout, "Đăng xuất", route = "account", action = { UserViewModel().singOut() })
}

