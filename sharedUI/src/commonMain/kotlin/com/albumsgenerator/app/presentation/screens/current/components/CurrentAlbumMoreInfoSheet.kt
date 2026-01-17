package com.albumsgenerator.app.presentation.screens.current.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_genres
import albumsgenerator.sharedui.generated.resources.album_other_streaming_services
import albumsgenerator.sharedui.generated.resources.album_rating_accessibility
import albumsgenerator.sharedui.generated.resources.album_rating_global_accessibility
import albumsgenerator.sharedui.generated.resources.album_subgenres
import albumsgenerator.sharedui.generated.resources.current_album_history
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.presentation.common.components.AlbumGenres
import com.albumsgenerator.app.presentation.common.components.AlbumStreamingServices
import com.albumsgenerator.app.presentation.common.components.NetworkImage
import com.albumsgenerator.app.presentation.common.components.Section
import com.albumsgenerator.app.presentation.common.components.SectionHeader
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentAlbumMoreInfoSheet(
    onDismiss: () -> Unit,
    album: Album,
    previousAlbums: List<History>,
    onOpenUri: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun closeSheet(action: () -> Unit) {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onDismiss()
                action()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
    ) {
        CurrentAlbumMoreInfoSheetContent(
            album = album,
            previousAlbums = previousAlbums,
            onOpenUri = { uri ->
                closeSheet {
                    onOpenUri(uri)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = Paddings.large),
        )
    }
}

@Composable
private fun CurrentAlbumMoreInfoSheetContent(
    album: Album,
    previousAlbums: List<History>,
    onOpenUri: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.large),
    ) {
        AlbumInfo(
            album = album,
            modifier = Modifier
                .semantics(mergeDescendants = true) {},
        )

        if (album.genres.isNotEmpty()) {
            Section(
                header = stringResource(Res.string.album_genres),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                AlbumGenres(
                    genres = album.genres,
                    subgenres = emptyList(),
                )
            }
        }

        if (album.subGenres.isNotEmpty()) {
            Section(
                header = stringResource(Res.string.album_subgenres),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                AlbumGenres(
                    genres = emptyList(),
                    subgenres = album.subGenres,
                )
            }
        }

        if (album.streamingServices.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SectionHeader(text = stringResource(Res.string.album_other_streaming_services))

                AlbumStreamingServices(
                    album = album,
                    openUri = onOpenUri,
                )
            }
        }

        if (previousAlbums.isNotEmpty()) {
            Section(
                header = stringResource(Res.string.current_album_history),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                PreviousAlbumsList(previousAlbums = previousAlbums)
            }
        }
    }
}

@Composable
private fun AlbumInfo(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Paddings.large),
    ) {
        NetworkImage(
            url = album.coverUrl,
            modifier = Modifier
                .weight(1 / 3f)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.large),
        )

        Column(
            modifier = Modifier
                .weight(2 / 3f),
            verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
        ) {
            Text(
                text = album.name,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = album.artist,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = album.releaseDate,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = album.artistOrigin.orEmpty().uppercase(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
private fun PreviousAlbumsList(
    previousAlbums: List<History>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.small),
    ) {
        for (previousAlbum in previousAlbums) {
            PreviousAlbumListItem(history = previousAlbum)
        }
    }
}

@Composable
private fun PreviousAlbumListItem(
    history: History,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Paddings.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Paddings.small),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .semantics(mergeDescendants = true) {},
            ) {
                Text(
                    text = history.album.name,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = history.album.releaseDate,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            if (history.hasRating) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .semantics(mergeDescendants = true) {},
                ) {
                    if (history.rating != null) {
                        val ratingDescription = stringResource(
                            Res.string.album_rating_accessibility,
                            history.rating,
                        )
                        Text(
                            text = history.rating,
                            modifier = Modifier
                                .semantics {
                                    contentDescription = ratingDescription
                                },
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    val globalRatingDescription = stringResource(
                        Res.string.album_rating_global_accessibility,
                        history.globalRating,
                    )
                    Text(
                        text = "(${history.globalRating})",
                        modifier = Modifier
                            .semantics {
                                contentDescription = globalRatingDescription
                            },
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentAlbumMoreInfoSheetPreview() {
    AppTheme {
        CurrentAlbumMoreInfoSheetContent(
            album = PreviewData.album,
            previousAlbums = listOf(PreviewData.history),
            onOpenUri = {},
            modifier = Modifier
                .padding(all = Paddings.large),
        )
    }
}
