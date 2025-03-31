package com.mobile.jobsearchapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

object DeviceWidthUtils {
    @Composable
    fun deviceWidth(): Int {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp
        return screenWidthDp
    }

    @Composable
    fun deviceWidthInPx(): Float {
        val configuration = LocalConfiguration.current
        val density = LocalDensity.current
        val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
        return screenWidthPx
    }
}