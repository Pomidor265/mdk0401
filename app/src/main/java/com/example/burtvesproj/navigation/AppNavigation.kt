package com.example.burtvesproj.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.burtvesproj.data.BookmarkRepository
import com.example.burtvesproj.data.MockData
import com.example.burtvesproj.screens.ListScreen
import com.example.burtvesproj.screens.MainMenuScreen
import com.example.burtvesproj.screens.SearchScreen
import com.example.burtvesproj.ui.screens.*
import java.net.URLDecoder
import java.net.URLEncoder

object Routes {
    const val MAIN_MENU = "main_menu"
    const val SEARCH_SCREEN = "search_screen" // ← НОВЫЙ
    const val APPS_SCREEN = "apps_screen"
    const val DEVICES_SCREEN = "devices_screen"
    const val INSTRUCTION_SCREEN = "instruction_screen"
    const val BOOKMARKS_SCREEN = "bookmarks_screen"
    const val BOOKMARK_DETAIL_SCREEN = "bookmark_detail_screen"
    const val GRAPHIC_INSTRUCTION_SCREEN = "graphic_instruction_screen"
    const val SETTINGS_SCREEN = "settings_screen"


    fun graphicInstruction(title: String): String {
        return "$GRAPHIC_INSTRUCTION_SCREEN/${enc(title)}"
    }

    fun instruction(title: String, text: String): String {
        return "$INSTRUCTION_SCREEN/${enc(title)}/${enc(text)}"
    }
    fun bookmarkDetail(bookmarkId: String): String {
        return "$BOOKMARK_DETAIL_SCREEN/$bookmarkId"
    }
    private fun enc(s: String) = URLEncoder.encode(s, "UTF-8")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val goHome: () -> Unit = {
        navController.navigate(Routes.MAIN_MENU) {
            popUpTo(Routes.MAIN_MENU) { inclusive = true }
            launchSingleTop = true
        }
    }
    val goBookmarks: () -> Unit = {
        navController.navigate(Routes.BOOKMARKS_SCREEN) { launchSingleTop = true }
    }
    val goSearch: () -> Unit = {
        navController.navigate(Routes.SEARCH_SCREEN) { launchSingleTop = true }
    }
    val goSettings: () -> Unit = {
        navController.navigate(Routes.SETTINGS_SCREEN) { launchSingleTop = true }
    }


    NavHost(navController = navController, startDestination = Routes.MAIN_MENU) {

        composable(Routes.MAIN_MENU) {
            MainMenuScreen(
                onAppsClick = { navController.navigate(Routes.APPS_SCREEN) },
                onDevicesClick = { navController.navigate(Routes.DEVICES_SCREEN) },
                onBookmarksClick = goBookmarks,
                onSearchClick = goSearch,
                onHomeClick = { /* уже здесь */ },
                onSettingsClick = goSettings
            )
        }

        composable(Routes.SETTINGS_SCREEN) {
            SettingsScreen(
                onHomeClick = goHome,
                onBookmarksClick = goBookmarks,
                onSearchClick = goSearch
            )
        }

        composable(Routes.SEARCH_SCREEN) {
            SearchScreen(
                onHomeClick = goHome,
                onBookmarksClick = goBookmarks,
                onSearchResultClick = { item ->
                    navController.navigate(Routes.instruction(item.title, item.instructionText))
                },
                onSettingsClick = goSettings
            )
        }

        composable(Routes.APPS_SCREEN) {
            ListScreen(
                title = "Приложения",
                data = MockData.getAppsData(),
                onBackClick = { navController.popBackStack() },
                onItemClick = { item ->
                    navController.navigate(Routes.instruction(item.title, item.instructionText))
                },
                onHomeClick = goHome,
                onBookmarksClick = goBookmarks,
                onSearchClick = goSearch,
                onSettingsClick = goSettings
            )
        }

        composable(Routes.DEVICES_SCREEN) {
            ListScreen(
                title = "Устройства",
                data = MockData.getDevicesData(),
                onBackClick = { navController.popBackStack() },
                onItemClick = { item ->
                    navController.navigate(Routes.instruction(item.title, item.instructionText))
                },
                onHomeClick = goHome,
                onBookmarksClick = goBookmarks,
                onSearchClick = goSearch,
                onSettingsClick = goSettings
            )
        }

        composable(
            route = "${Routes.INSTRUCTION_SCREEN}/{title}/{text}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("text") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""
            val text = backStackEntry.arguments?.getString("text")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""

            InstructionScreen(
                title = title,
                instructionText = text,
                onBackClick = { navController.popBackStack() },
                onHomeClick = goHome,
                onBookmarksClick = goBookmarks,
                onSearchClick = goSearch,
                onGraphicClick = {
                    navController.navigate(Routes.graphicInstruction(title))
                },
                onSettingsClick = goSettings
            )
        }

        composable(Routes.BOOKMARKS_SCREEN) {
            BookmarksScreen(
                onBackClick = { navController.popBackStack() },
                onHomeClick = goHome,
                onSearchClick = goSearch,
                onBookmarkClick = { bookmark ->
                    navController.navigate(Routes.bookmarkDetail(bookmark.id))
                },
                onSettingsClick = goSettings
            )
        }

        composable(
            route = "${Routes.BOOKMARK_DETAIL_SCREEN}/{bookmarkId}",
            arguments = listOf(navArgument("bookmarkId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookmarkId = backStackEntry.arguments?.getString("bookmarkId") ?: ""
            val bookmarks by BookmarkRepository.bookmarks.collectAsState()
            val bookmark = bookmarks.firstOrNull { it.id == bookmarkId }

            if (bookmark == null) {
                androidx.compose.runtime.LaunchedEffect(Unit) { navController.popBackStack() }
            } else {
                BookmarkDetailScreen(
                    bookmark = bookmark,
                    onBackClick = { navController.popBackStack() },
                    onHomeClick = goHome,
                    onSearchClick = goSearch,
                    onBookmarksClick = { /* уже здесь */ },
                    onInstructionClick = { instruction ->
                        navController.navigate(Routes.instruction(instruction.title, instruction.content))
                    },
                    onSettingsClick = goSettings
                )
            }
        }

        composable(
            route = "${Routes.GRAPHIC_INSTRUCTION_SCREEN}/{title}",
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")?.let {
                URLDecoder.decode(it, "UTF-8")
            } ?: ""
            val allData = MockData.getAppsData() + MockData.getDevicesData()
            val item = allData.firstOrNull { it.title == title }

            GraphicInstructionScreen(
                title = title,
                images = item?.graphicStepImages ?: emptyList(),
                onBackClick = { navController.popBackStack() },
                onBackToTextClick = {
                    val text = item?.instructionText ?: ""
                    navController.navigate(Routes.instruction(title, text)) {
                        popUpTo(Routes.INSTRUCTION_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onHomeClick = goHome,
                onBookmarksClick = goBookmarks,
                onSearchClick = goSearch,
                onSettingsClick = goSettings
            )
        }
    }
}