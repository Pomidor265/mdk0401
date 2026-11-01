package com.example.burtvesproj.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SettingsManager {

    var isDarkTheme by mutableStateOf(false)

    var textSize by mutableStateOf(1)

    fun getFontSize(): Int {
        return when (textSize) {
            0 -> 14
            1 -> 16
            2 -> 20
            else -> 16
        }
    }
}