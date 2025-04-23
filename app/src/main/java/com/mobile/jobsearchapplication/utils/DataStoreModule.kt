package com.mobile.jobsearchapplication.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "job_search_prefs")

val Context.dataStoreTheme: DataStore<Preferences> by preferencesDataStore(name = "dark_theme_prefs")

// Khóa để lưu trạng thái dark theme
private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_enabled")

class ThemePreferences(private val context: Context) {

    val isDarkTheme: Flow<Boolean> = context.dataStoreTheme.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }

    suspend fun saveThemeToDataStore(isDark: Boolean) {
        context.dataStoreTheme.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDark
        }
    }
}