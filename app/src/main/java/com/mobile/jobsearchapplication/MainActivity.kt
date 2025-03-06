package com.mobile.jobsearchapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobile.jobsearchapplication.ui.screens.UserScreen
import com.mobile.jobsearchapplication.ui.screens.DetailUserScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "detailUserScreen") {
                composable("userScreen") { UserScreen(navController) }
                composable("detailUserScreen") { DetailUserScreen() }
            }
        }
    }
}
