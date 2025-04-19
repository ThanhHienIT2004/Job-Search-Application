package com.mobile.jobsearchapplication.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobile.jobsearchapplication.ui.features.auth.AuthScreen
import com.mobile.jobsearchapplication.ui.features.filter.PostFilterScreen
import com.mobile.jobsearchapplication.ui.features.home.HomeScreen
import com.mobile.jobsearchapplication.ui.features.jobDetail.JobDetailScreen
import com.mobile.jobsearchapplication.ui.features.notification.NotificationScreen
import com.mobile.jobsearchapplication.ui.features.post.PostScreen
import com.mobile.jobsearchapplication.ui.features.profile.ProfileScreen
import com.mobile.jobsearchapplication.ui.features.saved.SavedScreen
import com.mobile.jobsearchapplication.ui.features.search.SearchScreen
import com.mobile.jobsearchapplication.ui.features.user.UserScreen

@Composable
fun AppNavigation() {
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

        composable("notificationsState") { NotificationScreen(navController) }
        composable("account") { UserScreen(navController) }
        composable("profile_screen") { ProfileScreen(navController) }
        composable("favorite_screen") { SavedScreen(navController) }
        composable("posted_screen") {  SavedScreen(navController) }
        composable("auth_screen") {  AuthScreen(navController) }
        composable("search_screen") {
            SearchScreen(navController)
        }




//            composable("detail_job_screen") { JobDetailScreen(navController) }
        composable("adv_job_search/{query}") { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            PostFilterScreen(navController, query)
        }

        composable(
            route = "job_detail_screen/{jobId}",
            arguments = listOf(navArgument("jobId") { type = NavType.StringType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }, // Bắt đầu từ bên phải
                    animationSpec = tween(700) // Thời gian animation: 300ms
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth }, // Thoát ra bên trái
                    animationSpec = tween(700)
                )
            }
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            JobDetailScreen(jobId = jobId ?: "", navController = navController)
        }
    }
}


class NavigationRoute {

    companion object {
        fun baseNavController(
            navController: NavController,
            route: String
        ) {
            navController.navigate(route){
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

}
