package com.example.burtvesproj.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.burtvesproj.components.RealItem
import com.example.burtvesproj.components.TopAppBar
import com.example.burtvesproj.data.AppItem
import com.example.burtvesproj.ui.components.BottomNavigationBar
import com.example.burtvesproj.ui.components.NavTab

@Composable
fun ListScreen(
    title: String,
    data: List<AppItem>,
    onBackClick: () -> Unit,
    onItemClick: (AppItem) -> Unit,
    onHomeClick: () -> Unit,
    onBookmarksClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(title = title, onBackClick = onBackClick)

        Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.background)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(data) { item ->
                    RealItem(item = item) { onItemClick(item) }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        BottomNavigationBar(
            selectedTab = NavTab.HOME,
            onHomeClick = onHomeClick,
            onBookmarksClick = onBookmarksClick,
            onSearchClick = onSearchClick,
            onSettingsClick = onSettingsClick
        )
    }
}