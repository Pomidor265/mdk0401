package com.example.burtvesproj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.burtvesproj.components.TopAppBar
import com.example.burtvesproj.data.Bookmark
import com.example.burtvesproj.data.BookmarkInstruction
import com.example.burtvesproj.data.BookmarkRepository
import com.example.burtvesproj.ui.components.BottomNavigationBar
import com.example.burtvesproj.ui.components.NavTab


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkDetailScreen(
    bookmark: Bookmark,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onBookmarksClick: () -> Unit,
    onInstructionClick: (BookmarkInstruction) -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopAppBar(title = bookmark.name, onBackClick = onBackClick)

        if (bookmark.instructions.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "В этой закладке пока нет инструкций.\n" +
                            "Откройте инструкцию и нажмите «закладку»,\n" +
                            "чтобы добавить её сюда.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookmark.instructions, key = { it.id }) { instruction ->
                    InstructionRow(
                        instruction = instruction,
                        onClick = { onInstructionClick(instruction) },
                        onDelete = {
                            BookmarkRepository.removeInstructionFromBookmark(bookmark.id, instruction.id)
                        }
                    )
                }
            }
        }

        BottomNavigationBar(
            selectedTab = NavTab.BOOKMARKS,
            onHomeClick = onHomeClick,
            onBookmarksClick = onBookmarksClick,
            onSearchClick = onSearchClick,
            onSettingsClick = onSettingsClick
        )
    }
}

@Composable
fun InstructionRow(
    instruction: BookmarkInstruction,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(instruction.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text(
                text = instruction.content,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 2
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = Color(0xFFD32F2F))
        }
    }
}