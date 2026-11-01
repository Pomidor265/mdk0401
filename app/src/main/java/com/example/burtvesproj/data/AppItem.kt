package com.example.burtvesproj.data
import androidx.annotation.DrawableRes

data class AppItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    @DrawableRes val imageResId: Int,
    val instructionText: String,
    val graphicStepImages: List<Int> = emptyList()
)