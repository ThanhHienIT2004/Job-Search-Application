package com.mobile.jobsearchapplication.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.mobile.jobsearchapplication.ui.features.auth.AuthScreen
import com.mobile.jobsearchapplication.ui.screens.DetailUserScreen
import com.mobile.jobsearchapplication.ui.screens.HomeScreen
import com.mobile.jobsearchapplication.ui.screens.JobDetailScreen
import com.mobile.jobsearchapplication.ui.screens.NotificationsScreen
import com.mobile.jobsearchapplication.ui.screens.PostFaveriteScreen
import com.mobile.jobsearchapplication.ui.screens.PostFilterScreen
import com.mobile.jobsearchapplication.ui.screens.PostScreen
import com.mobile.jobsearchapplication.ui.screens.PostedScreen
import com.mobile.jobsearchapplication.ui.screens.SearchScreen
import com.mobile.jobsearchapplication.ui.features.user.UserScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home_screen",
    ) {
        composable("home_screen") { HomeScreen(navController) }

        // Hiệu ứng trượt từ dưới lên khi vào màn hình "PostJob"
        composable(("post_screen"),
            enterTransition = {
                slideInVertically(initialOffsetY = { it }, animationSpec = tween(1200))
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { it }, animationSpec = tween(1200))
            }

        ) { PostScreen(navController) }

        composable("notifications") { NotificationsScreen(navController) }
        composable("account") { UserScreen(navController) }
        composable("detail_user_screen") { DetailUserScreen(navController) }
        composable("post_favorite") { PostFaveriteScreen(navController) }
        composable("posted_screen") {  PostedScreen(navController) }
        composable("auth_screen") {  AuthScreen(navController) }
        composable("search_screen") {  SearchScreen(navController) }



//            composable("detail_job_screen") { JobDetailScreen(navController) }
        composable("adv_job_search/{query}") { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            PostFilterScreen(navController, query)
        }

        composable("job_detail_screen/{query}") { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            JobDetailScreen(navController, query)
        }
    }
}
