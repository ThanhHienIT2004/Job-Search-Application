package com.mobile.jobsearchapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.mobile.jobsearchapplication.utils.ThemePreferences

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val themePreferences = ThemePreferences(LocalContext.current)
    val isDarkTheme = themePreferences.isDarkTheme.collectAsState(initial = false).value

    val colors = if (isDarkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
