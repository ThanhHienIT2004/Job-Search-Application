package com.mobile.jobsearchapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mobile.jobsearchapplication.ui.screens.components.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Không cho giao diện bị đẩy xuống
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // 🚀 Chỉ Ẩn Navigation Bar (Giữ nguyên Status Bar)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // 🔥 Đặt màu trong suốt cho Navigation Bar để tránh nhấp nháy
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        setContent {
            MainScreen()
        }
    }
}
