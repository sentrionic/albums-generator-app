package com.albumsgenerator.app.presentation.screens.year.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.top_book_albums
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.presentation.common.components.AlbumGrid
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.shared.components.AlbumWithArtistCard
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.utils.PreviewData
import org.jetbrains.compose.resources.stringResource

@Composable
fun YearContent(
    histories: List<History>,
    navigateToAlbum: (Route.Album) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    AlbumGrid(
        header = stringResource(Res.string.top_book_albums, histories.size),
        modifier = modifier,
        isLoading = isLoading,
    ) {
        items(
            items = histories,
            key = { it.album.uuid },
            contentType = { "album" },
        ) { history ->
            AlbumWithArtistCard(
                album = history.album,
                averageRating = history.globalRating,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable(
                        enabled = !isLoading,
                        onClickLabel = stringResource(Res.string.album_navigate_accessibility),
                        onClick = {
                            navigateToAlbum(
                                Route.Album(
                                    albumId = history.album.uuid,
                                    albumName = history.album.name,
                                ),
                            )
                        },
                    ),
                isLoading = isLoading,
            )
        }
    }
}

@Preview
@Composable
private fun YearContentPreview() {
    AppTheme {
        YearContent(
            histories = listOf(PreviewData.history),
            navigateToAlbum = {},
        )
    }
}
