package com.example.burtvesproj.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object BookmarkRepository {
    private const val PREFS_NAME = "bookmark_prefs"
    private const val KEY_BOOKMARKS = "bookmarks_list"

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> = _bookmarks.asStateFlow()

    fun init(context: Context){
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadFromStorage()
    }

    private fun saveToStorage(){
        val json = gson.toJson(_bookmarks.value)
        prefs.edit().putString(KEY_BOOKMARKS, json).apply()
    }

    private fun loadFromStorage(){
        val json = prefs.getString(KEY_BOOKMARKS, null) ?: return
        try {
            val type = object : TypeToken<List<Bookmark>>() {}.type
            val loaded: List<Bookmark> = gson.fromJson(json, type) ?: emptyList()
            _bookmarks.value = loaded
        }
        catch (e: Exception){
            _bookmarks.value = emptyList()
        }
    }

    fun createBookmark(name: String): Bookmark {
        val bookmark = Bookmark(name = name.trim())
        _bookmarks.update { it + bookmark }
        saveToStorage()
        return bookmark
    }

    fun deleteBookmark(id: String) {
        _bookmarks.update { list -> list.filterNot { it.id == id } }
        saveToStorage()
    }

    fun renameBookmark(id: String, newName: String) {
        _bookmarks.update { list ->
            list.map { if (it.id == id) it.copy(name = newName.trim()) else it }
        }
        saveToStorage()
    }


    fun addInstructionToBookmark(bookmarkId: String, instruction: BookmarkInstruction): Boolean {
        val bookmark = _bookmarks.value.firstOrNull { it.id == bookmarkId } ?: return false
        val exists = bookmark.instructions.any {
            it.title == instruction.title && it.content == instruction.content
        }
        if (exists) return false

        _bookmarks.update { list ->
            list.map {
                if (it.id == bookmarkId) it.copy(instructions = it.instructions + instruction) else it
            }
        }
        saveToStorage()
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
        saveToStorage()
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