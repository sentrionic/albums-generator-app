package com.albumsgenerator.app.presentation.screens.album.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_rating_difference
import albumsgenerator.sharedui.generated.resources.album_rating_label
import albumsgenerator.sharedui.generated.resources.album_stats
import albumsgenerator.sharedui.generated.resources.album_stats_average_rating
import albumsgenerator.sharedui.generated.resources.album_stats_vote_distribution
import albumsgenerator.sharedui.generated.resources.album_stats_votes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.presentation.common.components.Section
import com.albumsgenerator.app.presentation.common.components.StatDisplay
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.albumsgenerator.app.presentation.utils.format
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlbumStatsSection(
    stats: AlbumStats,
    history: History,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val density = LocalDensity.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Section(header = stringResource(Res.string.album_stats)) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(visible = isLoading),
                horizontalArrangement = Arrangement.spacedBy(
                    space = Paddings.medium,
                    alignment = Alignment.CenterHorizontally,
                ),
                verticalArrangement = Arrangement.Center,
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                StatDisplay(
                    label = stringResource(Res.string.album_stats_average_rating),
                    value = "${stats.averageRating}",
                )

                val rating = history.rating?.toIntOrNull()
                if (rating != null) {
                    StatDisplay(
                        label = stringResource(Res.string.album_rating_label),
                        value = "$rating",
                    )

                    StatDisplay(
                        label = stringResource(Res.string.album_rating_difference),
                        value = (rating - history.globalRating).toFloat().format(),
                    )
                }

                StatDisplay(
                    label = stringResource(Res.string.album_stats_votes),
                    value = "${stats.votes}",
                )
            }
        }

        Section(header = stringResource(Res.string.album_stats_vote_distribution)) {
            AlbumVoteDistribution(
                stats = stats,
                modifier = Modifier
                    .height(100.dp * density.fontScale)
                    .placeholder(visible = isLoading),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumStatsSectionPreview() {
    AppTheme {
        AlbumStatsSection(
            stats = PreviewData.stats,
            history = PreviewData.history,
        )
    }
}
