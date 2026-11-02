package com.example.burtvesproj.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SettingsManager {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_DARK_THEME = "is_dark_theme"
    private const val KEY_TEXT_SIZE = "text_size"

    private  lateinit var prefs: SharedPreferences

    var isDarkTheme by mutableStateOf(false)
        private set

    var textSize by mutableStateOf(1)
        private set

    fun init(context: Context){
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        isDarkTheme = prefs.getBoolean(KEY_DARK_THEME, false)
        textSize = prefs.getInt(KEY_TEXT_SIZE, 1)
    }

    fun updateTheme(isDark: Boolean){
        isDarkTheme = isDark
        prefs.edit().putBoolean(KEY_DARK_THEME, isDark).apply()
    }

    fun updateTextSize(size: Int){
        textSize = size
        prefs.edit().putInt(KEY_TEXT_SIZE, size).apply()
    }

    fun getFontSize(): Int {
        return when (textSize) {
            0 -> 16
            1 -> 20
            2 -> 24
            else -> 20
        }
    }
}