package com.example.burtvesproj.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class NavTab { SEARCH, HOME, BOOKMARKS, SETTINGS }

@Composable
fun BottomNavigationBar(
    selectedTab: NavTab = NavTab.HOME,
    onSearchClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onBookmarksClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavIcon(
            icon = Icons.Default.Search,
            label = "Search",
            isSelected = selectedTab == NavTab.SEARCH,
            onClick = onSearchClick
        )
        NavIcon(
            icon = Icons.Default.Home,
            label = "Home",
            isSelected = selectedTab == NavTab.HOME,
            onClick = onHomeClick
        )
        NavIcon(
            icon = if (selectedTab == NavTab.BOOKMARKS) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            label = "Bookmarks",
            isSelected = selectedTab == NavTab.BOOKMARKS,
            onClick = onBookmarksClick
        )
        NavIcon(
            icon = Icons.Default.Settings,
            label = "Settings",
            isSelected = selectedTab == NavTab.SETTINGS,
            onClick = onSettingsClick
        )
    }
}

@Composable
private fun NavIcon(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(28.dp)
        )
    }
}