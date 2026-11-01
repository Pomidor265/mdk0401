package com.example.burtvesproj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.burtvesproj.data.SettingsManager
import com.example.burtvesproj.ui.components.BottomNavigationBar
import com.example.burtvesproj.ui.components.NavTab


@Composable
fun SettingsScreen(
    onHomeClick: () -> Unit,
    onBookmarksClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    var showTextSizeDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    val bgColor = if (SettingsManager.isDarkTheme) Color(0xFF2E2E2E) else Color(0xFF9E9E9E)
    val textColor = if (SettingsManager.isDarkTheme) Color.White else Color.Black

    val currentTextSizeName = when (SettingsManager.textSize) {
        0 -> "Мелкий"
        1 -> "Средний"
        2 -> "Крупный"
        else -> "Средний"
    }
    val currentThemeName = if (SettingsManager.isDarkTheme) "Темная" else "Светлая"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Настройки",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTextSizeDialog = true }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Размер текста",
                    fontSize = 20.sp,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(28.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentTextSizeName,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showThemeDialog = true }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Тема",
                    fontSize = 20.sp,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(28.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentThemeName,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }

        BottomNavigationBar(
            selectedTab = NavTab.SETTINGS,
            onSearchClick = onSearchClick,
            onHomeClick = onHomeClick,
            onBookmarksClick = onBookmarksClick,
            onSettingsClick = { /* уже здесь */ }
        )
    }

    if (showTextSizeDialog) {
        val options = listOf("Мелкий", "Средний", "Крупный")
        AlertDialog(
            onDismissRequest = { showTextSizeDialog = false },
            title = { Text("Размер текста") },
            text = {
                Column {
                    options.forEachIndexed { index, option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    SettingsManager.textSize = index
                                    showTextSizeDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = SettingsManager.textSize == index,
                                onClick = {
                                    SettingsManager.textSize = index
                                    showTextSizeDialog = false
                                }
                            )
                            Text(option, fontSize = 16.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTextSizeDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("Тема") },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                SettingsManager.isDarkTheme = false
                                showThemeDialog = false
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !SettingsManager.isDarkTheme,
                            onClick = {
                                SettingsManager.isDarkTheme = false
                                showThemeDialog = false
                            }
                        )
                        Text("Светлая", fontSize = 16.sp)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                SettingsManager.isDarkTheme = true
                                showThemeDialog = false
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = SettingsManager.isDarkTheme,
                            onClick = {
                                SettingsManager.isDarkTheme = true
                                showThemeDialog = false
                            }
                        )
                        Text("Темная", fontSize = 16.sp)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}