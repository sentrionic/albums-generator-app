package com.albumsgenerator.app.presentation.screens.album.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.artist_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.year_navigate_accessibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlbumInfo(
    name: String,
    artist: String,
    releaseDate: String,
    navigateToArtist: (Route.Artist) -> Unit,
    navigateToYear: (Route.Year) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = name,
            modifier = Modifier
                .placeholder(visible = isLoading),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            text = artist,
            modifier = Modifier
                .clickable(
                    enabled = !isLoading,
                    onClickLabel = stringResource(Res.string.artist_navigate_accessibility),
                    onClick = {
                        navigateToArtist(Route.Artist(artist))
                    },
                )
                .placeholder(visible = isLoading),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = releaseDate,
            modifier = Modifier
                .clickable(
                    enabled = !isLoading,
                    onClickLabel = stringResource(Res.string.year_navigate_accessibility),
                    onClick = {
                        navigateToYear(Route.Year(releaseDate))
                    },
                )
                .placeholder(visible = isLoading),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun AlbumInfoPreview() {
    AppTheme {
        AlbumInfo(
            name = PreviewData.album.name,
            artist = PreviewData.album.artist,
            releaseDate = PreviewData.album.releaseDate,
            navigateToArtist = {},
            navigateToYear = {},
        )
    }
}
