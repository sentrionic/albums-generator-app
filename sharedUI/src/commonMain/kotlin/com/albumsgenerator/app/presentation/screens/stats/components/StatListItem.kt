package com.albumsgenerator.app.presentation.screens.stats.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_rating_global_accessibility
import albumsgenerator.sharedui.generated.resources.statistics_item_votes_accessibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatListItem(
    stat: AlbumStats,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(all = Paddings.large),
            horizontalArrangement = Arrangement.spacedBy(Paddings.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = stat.name,
                    modifier = Modifier
                        .placeholder(visible = isLoading),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = stat.artist,
                    modifier = Modifier
                        .placeholder(visible = isLoading),
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
            ) {
                val averageRatingDescription =
                    stringResource(Res.string.album_rating_global_accessibility, stat.averageRating)
                Text(
                    text = "${stat.averageRating}",
                    modifier = Modifier
                        .semantics {
                            contentDescription = averageRatingDescription
                        }
                        .placeholder(visible = isLoading),
                    style = MaterialTheme.typography.titleSmall,
                )

                val votesDescription =
                    stringResource(Res.string.statistics_item_votes_accessibility, stat.votes)
                Text(
                    text = stat.votes.toString(),
                    modifier = Modifier
                        .semantics {
                            contentDescription = votesDescription
                        }
                        .placeholder(visible = isLoading),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlbumListItemPreview() {
    AppTheme {
        StatListItem(
            stat = PreviewData.stats,
        )
    }
}
