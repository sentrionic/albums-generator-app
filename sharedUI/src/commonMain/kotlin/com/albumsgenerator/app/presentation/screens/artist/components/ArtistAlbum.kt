package com.albumsgenerator.app.presentation.screens.artist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.presentation.common.components.NetworkImage
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun ArtistAlbum(
    album: Album,
    averageRating: Double,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Card(modifier = modifier) {
        Column {
            NetworkImage(
                url = album.images.getOrNull(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .placeholder(isLoading),
            )

            Column(
                modifier = Modifier
                    .padding(all = Paddings.large),
                verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
            ) {
                Text(
                    text = album.name,
                    modifier = Modifier
                        .placeholder(isLoading),
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    minLines = 2,
                    maxLines = 2,
                )

                Text(
                    text = album.releaseDate,
                    modifier = Modifier
                        .placeholder(isLoading),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Text(
                    text = "$averageRating / 5",
                    modifier = Modifier
                        .placeholder(isLoading),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ArtistAlbumPreview() {
    AppTheme {
        ArtistAlbum(
            album = PreviewData.album,
            averageRating = PreviewData.stats.averageRating,
        )
    }
}
