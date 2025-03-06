package com.mobile.jobsearchapplication.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getRelativeDate(date: Date): String {
        val now = Calendar.getInstance()
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return when {
            sdf.format(date) == sdf.format(today.time) -> "Hôm nay"
            sdf.format(date) == sdf.format(yesterday.time) -> "Hôm qua"
            else -> sdf.format(date)
        }
    }
}
