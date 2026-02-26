package com.albumsgenerator.app.presentation.screens.album.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.action_error
import albumsgenerator.sharedui.generated.resources.album_genres
import albumsgenerator.sharedui.generated.resources.album_streaming_services
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.components.AlbumGenres
import com.albumsgenerator.app.presentation.common.components.AlbumStreamingServices
import com.albumsgenerator.app.presentation.common.components.NetworkImage
import com.albumsgenerator.app.presentation.common.components.Section
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.album.AlbumState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumContent(
    state: AlbumState,
    showMessage: (String) -> Unit,
    navigateTo: (Route) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val scope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current

    fun tryOpenUri(uri: String) {
        try {
            uriHandler.openUri(uri)
        } catch (_: Exception) {
            scope.launch {
                showMessage(getString(Res.string.action_error))
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        state.coverUrl?.let { coverUrl ->
            NetworkImage(
                url = coverUrl,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .aspectRatio(1f)
                    .clip(shape = MaterialTheme.shapes.large)
                    .placeholder(
                        visible = isLoading,
                        shape = MaterialTheme.shapes.large,
                    ),
            )
        }

        AlbumInfo(
            name = state.name,
            artist = state.artist,
            releaseDate = state.releaseDate,
            navigateToArtist = navigateTo,
            navigateToYear = navigateTo,
            isLoading = isLoading,
        )

        if (state.streamingServices.isNotEmpty()) {
            Section(header = stringResource(Res.string.album_streaming_services)) {
                AlbumStreamingServices(
                    streamingServices = state.streamingServices,
                    serviceUrl = state::serviceUrl,
                    openUri = ::tryOpenUri,
                    modifier = Modifier
                        .placeholder(visible = isLoading),
                )
            }
        }

        AlbumStatsSection(
            stats = state.stats,
            history = state.history,
            isLoading = isLoading,
        )

        Section(header = stringResource(Res.string.album_genres)) {
            AlbumGenres(
                genres = state.genres,
                subgenres = state.subGenres,
                onNavigateToGenre = {
                    navigateTo(Route.Genre(it))
                },
                modifier = Modifier
                    .placeholder(visible = isLoading),
            )
        }

        val summary = state.history?.album?.summary
        val wikipediaUrl = state.history?.album?.responsiveWikipediaUrl
        if (!summary.isNullOrEmpty() && !wikipediaUrl.isNullOrEmpty()) {
            AlbumSummary(
                summary = summary,
                openSummaryPage = {
                    navigateTo(Route.Web(wikipediaUrl, "Wikipedia"))
                },
                isLoading = isLoading,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumContentPreview() {
    AppTheme {
        AlbumContent(
            state = AlbumState(
                history = PreviewData.history,
                stats = PreviewData.stats,
            ),
            showMessage = {},
            navigateTo = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumContentLoadingPreview() {
    AppTheme {
        AlbumContent(
            state = AlbumState(
                history = PreviewData.history,
                stats = PreviewData.stats,
            ),
            showMessage = {},
            navigateTo = {},
            isLoading = true,
        )
    }
}
