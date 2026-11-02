package com.example.burtvesproj.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object BookmarkRepository {

    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> = _bookmarks.asStateFlow()

    fun createBookmark(name: String): Bookmark {
        val bookmark = Bookmark(name = name.trim())
        _bookmarks.update { it + bookmark }
        return bookmark
    }

    fun deleteBookmark(id: String) {
        _bookmarks.update { list -> list.filterNot { it.id == id } }
    }

    fun renameBookmark(id: String, newName: String) {
        _bookmarks.update { list ->
            list.map { if (it.id == id) it.copy(name = newName.trim()) else it }
        }
    }


    fun addInstructionToBookmark(bookmarkId: String, instruction: BookmarkInstruction): Boolean {
        val bookmark = _bookmarks.value.firstOrNull { it.id == bookmarkId } ?: return false
        // Проверка: нет ли уже такой же инструкции в этой закладке
        val exists = bookmark.instructions.any {
            it.title == instruction.title && it.content == instruction.content
        }
        if (exists) return false

        _bookmarks.update { list ->
            list.map {
                if (it.id == bookmarkId) it.copy(instructions = it.instructions + instruction) else it
            }
        }
        return true
    }

    fun removeInstructionFromBookmark(bookmarkId: String, instructionId: String) {
        _bookmarks.update { list ->
            list.map {
                if (it.id == bookmarkId) {
                    it.copy(instructions = it.instructions.filterNot { i -> i.id == instructionId })
                } else it
            }
        }
    }

    fun isInstructionSaved(title: String, content: String): Boolean {
        return _bookmarks.value.any { bookmark ->
            bookmark.instructions.any { it.title == title && it.content == content }
        }
    }

    fun findBookmarksWithInstruction(title: String, content: String): List<Bookmark> {
        return _bookmarks.value.filter { bookmark ->
            bookmark.instructions.any { it.title == title && it.content == content }
        }
    }

    fun getBookmarkById(id: String): Bookmark? = _bookmarks.value.firstOrNull { it.id == id }
}