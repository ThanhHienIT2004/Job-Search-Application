package com.mobile.jobsearchapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

object DeviceSizeUtils {

    @Composable
    fun deviceHeight(): Int {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenHeightDp
        return screenWidthDp
    }

    @Composable
    fun deviceWidthInPx(): Float {
        val density = LocalDensity.current
        val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
        return screenWidthPx
    }
}