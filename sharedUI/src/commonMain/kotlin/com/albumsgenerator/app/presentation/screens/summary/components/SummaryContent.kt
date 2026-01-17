package com.albumsgenerator.app.presentation.screens.summary.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.summary_section_bottom_albums
import albumsgenerator.sharedui.generated.resources.summary_section_top_albums
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.summary.SummaryScreenState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

private object ContentTypes {
    const val SECTION_TITLE = "section-title"
    const val ALBUMS = "albums"
}

@Composable
fun SummaryContent(
    summary: SummaryScreenState,
    navigateTo: (Route) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    @Composable
    fun Modifier.gridItemModifier(album: Album) = clip(MaterialTheme.shapes.medium)
        .clickable(
            enabled = !isLoading,
            onClickLabel = stringResource(Res.string.album_navigate_accessibility),
            onClick = {
                navigateTo(Route.Album(albumId = album.uuid, albumName = album.name))
            },
        )

    @Composable
    fun Modifier.headerModifier() = semantics { heading() }
        .placeholder(visible = isLoading)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = PaddingValues(all = Paddings.large),
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
    ) {
        item(
            key = "header",
            span = { GridItemSpan(maxLineSpan) },
            contentType = "summary-header",
        ) {
            SummaryHeader(
                summary = summary,
                isLoading = isLoading,
            )
        }

        item(
            key = "5-Star Albums",
            span = { GridItemSpan(maxLineSpan) },
            contentType = ContentTypes.SECTION_TITLE,
        ) {
            SectionHeader(
                title = stringResource(
                    Res.string.summary_section_top_albums,
                    summary.fiveStarAlbums.size,
                ),
                modifier = Modifier
                    .headerModifier(),
            )
        }

        items(
            items = summary.fiveStarAlbums,
            key = { it.uuid },
            contentType = { ContentTypes.ALBUMS },
        ) { album ->
            SummaryGridItem(
                album = album,
                modifier = Modifier
                    .gridItemModifier(album),
                isLoading = isLoading,
            )
        }

        item(
            key = "1-Star Albums",
            span = { GridItemSpan(maxLineSpan) },
            contentType = ContentTypes.SECTION_TITLE,
        ) {
            SectionHeader(
                title = stringResource(
                    Res.string.summary_section_bottom_albums,
                    summary.oneStarAlbums.size,
                ),
                modifier = Modifier
                    .headerModifier(),
            )
        }

        items(
            items = summary.oneStarAlbums,
            key = { it.uuid },
            contentType = { ContentTypes.ALBUMS },
        ) { album ->
            SummaryGridItem(
                album = album,
                modifier = Modifier
                    .gridItemModifier(album),
                isLoading = isLoading,
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        modifier = modifier,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Preview
@Composable
private fun SummaryContentPreview() {
    AppTheme {
        SummaryContent(
            summary = SummaryScreenState(
                albumsRated = 123,
                averageRating = 3.0f,
                percentageComplete = 0.25f,
                fiveStarAlbums = listOf(PreviewData.album),
                oneStarAlbums = listOf(PreviewData.album),
            ),
            navigateTo = {},
        )
    }
}
