package com.albumsgenerator.app.presentation.screens.stats.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.statistics_section_controversial
import albumsgenerator.sharedui.generated.resources.statistics_section_highest_rated
import albumsgenerator.sharedui.generated.resources.statistics_section_lowest_rated
import albumsgenerator.sharedui.generated.resources.statistics_section_uncontroversial
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.stats.StatsScreenState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

private object ContentTypes {
    const val SECTION_TITLE = "section-title"
    const val ALBUMS = "albums"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsContent(
    state: StatsScreenState,
    navigateTo: (Route) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    @Composable
    fun Modifier.listItemModifier(stat: AlbumStats) = fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .clickable(
            enabled = !isLoading,
            onClickLabel = stringResource(Res.string.album_navigate_accessibility),
            onClick = {
                navigateTo(
                    Route.Album(
                        albumId = "",
                        albumName = stat.name,
                        albumArtist = stat.artist,
                    ),
                )
            },
        )

    @Composable
    fun Modifier.headerModifier() = padding(vertical = Paddings.medium)
        .semantics { heading() }
        .placeholder(visible = isLoading)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = Paddings.large),
        verticalArrangement = Arrangement.spacedBy(Paddings.small),
    ) {
        item(
            key = "header",
            contentType = "stats-header",
        ) {
            StatsHeader(
                state = state,
                isLoading = isLoading,
            )
        }

        item(
            key = "Highest Rated Albums",
            contentType = ContentTypes.SECTION_TITLE,
        ) {
            SectionHeader(
                title = stringResource(Res.string.statistics_section_highest_rated),
                modifier = Modifier
                    .headerModifier(),
            )
        }

        items(
            items = state.topAlbums,
            key = { it.name },
            contentType = { ContentTypes.ALBUMS },
        ) { stat ->
            StatListItem(
                stat = stat,
                modifier = Modifier
                    .listItemModifier(stat),
                isLoading = isLoading,
            )
        }

        item(
            key = "Lowest Rated Albums",
            contentType = ContentTypes.SECTION_TITLE,
        ) {
            SectionHeader(
                title = stringResource(Res.string.statistics_section_lowest_rated),
                modifier = Modifier
                    .headerModifier(),
            )
        }

        items(
            items = state.bottomAlbums,
            key = { it.name },
            contentType = { ContentTypes.ALBUMS },
        ) { stat ->
            StatListItem(
                stat = stat,
                modifier = Modifier
                    .listItemModifier(stat),
                isLoading = isLoading,
            )
        }

        item(
            key = "Controversial Albums",
            contentType = ContentTypes.SECTION_TITLE,
        ) {
            SectionHeader(
                title = stringResource(Res.string.statistics_section_controversial),
                modifier = Modifier
                    .headerModifier(),
            )
        }

        items(
            items = state.mostControversial,
            key = { it.name },
            contentType = { ContentTypes.ALBUMS },
        ) { stat ->
            StatListItem(
                stat = stat,
                modifier = Modifier
                    .listItemModifier(stat),
                isLoading = isLoading,
            )
        }

        item(
            key = "Uncontroversial Albums",
            contentType = ContentTypes.SECTION_TITLE,
        ) {
            SectionHeader(
                title = stringResource(Res.string.statistics_section_uncontroversial),
                modifier = Modifier
                    .headerModifier(),
            )
        }

        items(
            items = state.leastControversial,
            key = { it.name },
            contentType = { ContentTypes.ALBUMS },
        ) { stat ->
            StatListItem(
                stat = stat,
                modifier = Modifier
                    .listItemModifier(stat),
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
        style = MaterialTheme.typography.titleLarge,
    )
}

@Preview(showBackground = true)
@Composable
private fun StatsContentPreview() {
    val albums = listOf(PreviewData.stats)

    AppTheme {
        StatsContent(
            state = StatsScreenState(
                topAlbums = albums,
                bottomAlbums = albums,
                mostControversial = albums,
                leastControversial = albums,
                votes = 12345678,
                averageRating = 3.0f,
            ),
            navigateTo = {},
        )
    }
}
