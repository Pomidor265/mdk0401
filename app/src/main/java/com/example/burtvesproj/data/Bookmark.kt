package com.example.burtvesproj.data

import java.util.UUID

data class BookmarkInstruction(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val addedAt: Long = System.currentTimeMillis()
)


data class Bookmark(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val instructions: List<BookmarkInstruction> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)