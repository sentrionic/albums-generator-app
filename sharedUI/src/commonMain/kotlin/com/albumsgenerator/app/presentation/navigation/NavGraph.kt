package com.albumsgenerator.app.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultPopTransitionSpec
import com.albumsgenerator.app.presentation.screens.album.AlbumScreen
import com.albumsgenerator.app.presentation.screens.artist.ArtistScreen
import com.albumsgenerator.app.presentation.screens.current.CurrentAlbumScreen
import com.albumsgenerator.app.presentation.screens.genre.GenreScreen
import com.albumsgenerator.app.presentation.screens.history.HistoryScreen
import com.albumsgenerator.app.presentation.screens.login.LoginScreen
import com.albumsgenerator.app.presentation.screens.rate.RateAlbumScreen
import com.albumsgenerator.app.presentation.screens.settings.SettingsScreen
import com.albumsgenerator.app.presentation.screens.splash.SplashScreen
import com.albumsgenerator.app.presentation.screens.stats.StatsScreen
import com.albumsgenerator.app.presentation.screens.summary.SummaryScreen
import com.albumsgenerator.app.presentation.screens.webview.WebViewScreen
import com.albumsgenerator.app.presentation.screens.year.YearScreen

@Composable
fun NavGraph(
    backStack: List<NavKey>,
    navigateTo: (NavKey) -> Unit,
    replaceWith: (NavKey) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = onBack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        popTransitionSpec = defaultPopTransitionSpec(),
        entryProvider = entryProvider {
            entry<Route.Splash> {
                SplashScreen()
            }

            entry<Route.Login> {
                LoginScreen(
                    onContinue = {
                        replaceWith(it)
                    },
                )
            }

            entry<Route.RateAlbum>(
                metadata = NavDisplay.popTransitionSpec {
                    slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
                } + NavDisplay.predictivePopTransitionSpec {
                    slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
                },
            ) { key ->
                RateAlbumScreen(
                    projectName = key.name,
                    onContinue = {
                        replaceWith(Route.CurrentAlbum)
                    },
                )
            }

            entry<Route.CurrentAlbum> {
                CurrentAlbumScreen(
                    navigateTo = navigateTo,
                )
            }

            entry<Route.History> {
                HistoryScreen(
                    navigateTo = navigateTo,
                )
            }

            entry<Route.Stats> {
                StatsScreen(
                    navigateTo = navigateTo,
                )
            }

            entry<Route.Summary> {
                SummaryScreen(
                    navigateTo = navigateTo,
                )
            }

            entry<Route.Settings> {
                SettingsScreen(
                    navigateTo = navigateTo,
                    onLogout = {
                        replaceWith(Route.Login)
                    },
                )
            }

            entry<Route.Album>(
                metadata = defaultTransitionSpec(),
            ) { key ->
                AlbumScreen(
                    data = key,
                    navigateTo = navigateTo,
                    onBack = onBack,
                )
            }

            entry<Route.Artist>(
                metadata = defaultTransitionSpec(),
            ) { key ->
                ArtistScreen(
                    data = key,
                    navigateTo = navigateTo,
                    onBack = onBack,
                )
            }

            entry<Route.Genre>(
                metadata = defaultTransitionSpec(),
            ) { key ->
                GenreScreen(
                    data = key,
                    navigateTo = navigateTo,
                    onBack = onBack,
                )
            }

            entry<Route.Year>(
                metadata = defaultTransitionSpec(),
            ) { key ->
                YearScreen(
                    data = key,
                    navigateTo = navigateTo,
                    onBack = onBack,
                )
            }

            entry<Route.Web>(
                metadata = defaultTransitionSpec(),
            ) { key ->
                WebViewScreen(
                    url = key.url,
                    title = key.title,
                    onBack = onBack,
                )
            }
        },
    )
}

private fun defaultTransitionSpec() = NavDisplay.transitionSpec {
    slideInHorizontally { it } + fadeIn() togetherWith
        slideOutHorizontally { -it } + fadeOut()
} + NavDisplay.popTransitionSpec {
    slideInHorizontally { -it } + fadeIn() togetherWith
        slideOutHorizontally { it } + fadeOut()
} + NavDisplay.predictivePopTransitionSpec {
    slideInHorizontally { -it } + fadeIn() togetherWith
        slideOutHorizontally { it } + fadeOut()
}
