package com.albumsgenerator.app.presentation.screens.genre.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.top_book_albums
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.components.AlbumGrid
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.genre.GenreState
import com.albumsgenerator.app.presentation.shared.components.AlbumWithArtistCard
import com.albumsgenerator.app.presentation.shared.components.UnknownAlbumArtistCard
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.utils.PreviewData
import org.jetbrains.compose.resources.stringResource

@Composable
fun GenreContent(
    state: GenreState,
    navigateToAlbum: (Route.Album) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    AlbumGrid(
        header = stringResource(Res.string.top_book_albums, state.stats.size),
        modifier = modifier,
        isLoading = isLoading,
    ) {
        items(
            items = state.stats,
            key = { it.name },
            contentType = { "album" },
        ) { stat ->
            val relatedHistory = remember {
                state.histories.firstOrNull {
                    it.album.name == stat.name &&
                        it.album.artist == stat.artist
                }
            }
            if (relatedHistory != null) {
                AlbumWithArtistCard(
                    album = relatedHistory.album,
                    averageRating = relatedHistory.globalRating,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable(
                            enabled = !isLoading,
                            onClickLabel = stringResource(Res.string.album_navigate_accessibility),
                            onClick = {
                                navigateToAlbum(
                                    Route.Album(
                                        albumId = relatedHistory.album.uuid,
                                        albumName = relatedHistory.album.name,
                                    ),
                                )
                            },
                        ),
                    isLoading = isLoading,
                )
            } else {
                UnknownAlbumArtistCard()
            }
        }
    }
}

@Preview
@Composable
private fun GenreContentPreview() {
    AppTheme {
        GenreContent(
            state = GenreState(
                histories = listOf(PreviewData.history),
                stats = listOf(PreviewData.stats),
            ),
            navigateToAlbum = {},
        )
    }
}
