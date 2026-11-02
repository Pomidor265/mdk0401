package com.example.burtvesproj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.burtvesproj.components.TopAppBar
import com.example.burtvesproj.data.BookmarkInstruction
import com.example.burtvesproj.data.BookmarkRepository
import com.example.burtvesproj.data.SettingsManager
import com.example.burtvesproj.ui.components.BottomNavigationBar
import com.example.burtvesproj.ui.components.NavTab
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionScreen(
    title: String,
    instructionText: String,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onBookmarksClick: () -> Unit,
    onSearchClick: () -> Unit,
    onGraphicClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val bookmarks by BookmarkRepository.bookmarks.collectAsState()
    val alreadySaved = BookmarkRepository.isInstructionSaved(title, instructionText)

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            TopAppBar(title = "Инструкция", onBackClick = onBackClick)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        if (bookmarks.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Сначала создайте закладку на экране «Закладки»"
                                )
                            }
                        } else {
                            showSheet = true
                        }
                    }) {
                        Icon(
                            imageVector = if (alreadySaved) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                            contentDescription = "В закладки",
                            tint = if (alreadySaved) Color(0xFF1976D2) else Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = instructionText,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = SettingsManager.getFontSize().sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onGraphicClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Графическая")
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

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Выберите закладку",
                        fontSize = 18.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))

                    bookmarks.forEach { bookmark ->
                        val contains = bookmark.instructions.any {
                            it.title == title && it.content == instructionText
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (contains) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Уже есть в «${bookmark.name}»"
                                            )
                                        }
                                    } else {
                                        val added = BookmarkRepository.addInstructionToBookmark(
                                            bookmark.id,
                                            BookmarkInstruction(
                                                title = title,
                                                content = instructionText
                                            )
                                        )
                                        scope.launch {
                                            if (added) {
                                                snackbarHostState.showSnackbar(
                                                    "Добавлено в «${bookmark.name}»"
                                                )
                                            } else {
                                                snackbarHostState.showSnackbar("Уже есть в этой закладке")
                                            }
                                        }
                                        showSheet = false
                                    }
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (contains) Icons.Default.Favorite
                                else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = if (contains) Color(0xFF1976D2) else Color.Gray
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    bookmark.name,
                                    fontSize = 16.sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                                )
                                Text(
                                    "Инструкций: ${bookmark.instructions.size}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            if (contains) {
                                Text("✓", color = Color(0xFF1976D2), fontSize = 18.sp)
                            }
                        }
                        Divider()
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}