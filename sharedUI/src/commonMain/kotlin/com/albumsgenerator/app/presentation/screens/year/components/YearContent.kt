package com.albumsgenerator.app.presentation.screens.year.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.top_book_albums
import albumsgenerator.sharedui.generated.resources.unknown_album
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.SpoilerMode
import com.albumsgenerator.app.presentation.common.components.AlbumGrid
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.top.TopState
import com.albumsgenerator.app.presentation.screens.top.TopState.Companion.key
import com.albumsgenerator.app.presentation.shared.components.AlbumWithArtistCard
import com.albumsgenerator.app.presentation.shared.components.UnknownAlbumArtistCard
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun YearContent(
    state: TopState,
    navigateToAlbum: (Route.Album) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    AlbumGrid(
        header = stringResource(Res.string.top_book_albums, state.items.size),
        modifier = modifier,
        isLoading = isLoading,
    ) {
        items(
            items = state.items,
            key = { it.key },
            contentType = { "album" },
        ) { (stat, history) ->
            if (history != null || state.spoilerMode == SpoilerMode.VISIBLE) {
                AlbumWithArtistCard(
                    stats = stat,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable(
                            enabled = !isLoading,
                            onClickLabel = stringResource(Res.string.album_navigate_accessibility),
                            onClick = {
                                navigateToAlbum(
                                    Route.Album(
                                        albumId = history?.album?.uuid.orEmpty(),
                                        albumName = stat.name,
                                        albumArtist = stat.artist,
                                    ),
                                )
                            },
                        ),
                    isLoading = isLoading,
                )
            } else if (state.spoilerMode == SpoilerMode.PARTIAL) {
                val description = stringResource(Res.string.unknown_album)
                UnknownAlbumArtistCard(
                    modifier = Modifier
                        .clearAndSetSemantics {
                            stateDescription = description
                        },
                )
            }
        }
    }
}

@Preview
@Composable
private fun YearContentPreview() {
    AppTheme {
        YearContent(
            state = TopState.EMPTY,
            navigateToAlbum = {},
        )
    }
}
