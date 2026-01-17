package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData

@Composable
fun AlbumGenres(
    genres: List<String>,
    subgenres: List<String>,
    modifier: Modifier = Modifier,
    onNavigateToGenre: (String) -> Unit = {},
) {
    val allGenres = remember(genres, subgenres) {
        (genres + subgenres).distinct()
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.small),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        for (genre in allGenres) {
            GenreChip(
                label = genre,
                onClick = {
                    onNavigateToGenre(genre)
                },
                enabled = genre in genres,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumGenresPreview() {
    AppTheme {
        AlbumGenres(
            genres = PreviewData.album.genres,
            subgenres = PreviewData.album.subGenres,
            onNavigateToGenre = {},
        )
    }
}
