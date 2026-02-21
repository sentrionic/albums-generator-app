package com.albumsgenerator.app.presentation.screens.artist.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.book_album
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
import com.albumsgenerator.app.presentation.screens.artist.ArtistState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.utils.PreviewData
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ArtistContent(
    state: ArtistState,
    navigateToAlbum: (Route.Album) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    AlbumGrid(
        header = pluralStringResource(
            Res.plurals.book_album,
            state.albumStats.size,
            state.albumStats.size,
        ),
        modifier = modifier,
        isLoading = isLoading,
    ) {
        items(
            items = state.albumStats,
            key = { it.name },
            contentType = { "album" },
        ) { stat ->
            val relatedAlbum = remember {
                state.albums.firstOrNull {
                    it.name == stat.name && it.artist == stat.artist
                }
            }

            if (relatedAlbum != null || !state.spoilerFree) {
                ArtistAlbum(
                    stat = stat,
                    averageRating = stat.averageRating,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable(
                            enabled = !isLoading,
                            onClickLabel = stringResource(Res.string.album_navigate_accessibility),
                            onClick = {
                                navigateToAlbum(
                                    Route.Album(
                                        albumId = relatedAlbum?.uuid.orEmpty(),
                                        albumName = stat.name,
                                        albumArtist = stat.artist,
                                    ),
                                )
                            },
                        ),
                    isLoading = isLoading,
                )
            } else {
                UnknownAlbum()
            }
        }
    }
}

@Preview
@Composable
private fun ArtistContentPreview() {
    AppTheme {
        ArtistContent(
            state = ArtistState(
                albums = listOf(PreviewData.album),
                albumStats = listOf(PreviewData.stats),
                spoilerFree = true,
            ),
            navigateToAlbum = {},
        )
    }
}
