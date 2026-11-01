package com.example.burtvesproj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.burtvesproj.components.TopAppBar
import com.example.burtvesproj.data.Bookmark
import com.example.burtvesproj.data.BookmarkRepository
import com.example.burtvesproj.ui.components.BottomNavigationBar
import com.example.burtvesproj.ui.components.NavTab

@Composable
fun BookmarksScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onBookmarkClick: (Bookmark) -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val bookmarks by BookmarkRepository.bookmarks.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }
    var editingBookmark by remember { mutableStateOf<Bookmark?>(null) }
    var bookmarkToDelete by remember { mutableStateOf<Bookmark?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TopAppBar(title = "Закладки", onBackClick = onBackClick)

            if (bookmarks.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет закладок.\nНажмите «+», чтобы создать.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(bookmarks, key = { it.id }) { bookmark ->
                        BookmarkCard(
                            bookmark = bookmark,
                            onClick = { onBookmarkClick(bookmark) },
                            onEdit = { editingBookmark = bookmark },
                            onDelete = { bookmarkToDelete = bookmark }
                        )
                    }
                }
            }

            BottomNavigationBar(
                selectedTab = NavTab.BOOKMARKS,
                onHomeClick = onHomeClick,
                onBookmarksClick = {},
                onSearchClick = onSearchClick,
                onSettingsClick = onSettingsClick
            )
        }

        FloatingActionButton(
            onClick = { showCreateDialog = true },
            containerColor = Color(0xFF1976D2),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Создать закладку")
        }
    }

    if (showCreateDialog) {
        BookmarkNameDialog(
            titleDialog = "Новая закладка",
            initialName = "",
            onDismiss = { showCreateDialog = false },
            onSave = { name ->
                BookmarkRepository.createBookmark(name)
                showCreateDialog = false
            }
        )
    }

    editingBookmark?.let { bookmark ->
        BookmarkNameDialog(
            titleDialog = "Переименовать закладку",
            initialName = bookmark.name,
            onDismiss = { editingBookmark = null },
            onSave = { newName ->
                BookmarkRepository.renameBookmark(bookmark.id, newName)
                editingBookmark = null
            }
        )
    }

    bookmarkToDelete?.let { bookmark ->
        AlertDialog(
            onDismissRequest = { bookmarkToDelete = null },
            title = { Text("Удалить закладку?") },
            text = { Text("«${bookmark.name}» и все её инструкции будут удалены.") },
            confirmButton = {
                TextButton(onClick = {
                    BookmarkRepository.deleteBookmark(bookmark.id)
                    bookmarkToDelete = null
                }) { Text("Удалить", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { bookmarkToDelete = null }) { Text("Отмена") }
            }
        )
    }
}

@Composable
fun BookmarkCard(
    bookmark: Bookmark,
    onClick: () -> Unit,
    onEdit: () -> Unit,
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
            Text(bookmark.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Инструкций: ${bookmark.instructions.size}",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        IconButton(onClick = onEdit) {
            Icon(Icons.Default.Edit, contentDescription = "Переименовать", tint = Color(0xFF616161))
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = Color(0xFFD32F2F))
        }
    }
}


@Composable
fun BookmarkNameDialog(
    titleDialog: String,
    initialName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(titleDialog) },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Название закладки") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { if (name.isNotBlank()) onSave(name.trim()) },
                enabled = name.isNotBlank()
            ) { Text(if (initialName.isEmpty()) "Создать" else "Сохранить") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        }
    )
}