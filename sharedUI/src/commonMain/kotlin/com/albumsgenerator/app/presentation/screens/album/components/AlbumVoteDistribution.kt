package com.albumsgenerator.app.presentation.screens.album.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_stats_vote_distribution_accessibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.presentation.common.components.Tooltip
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.albumsgenerator.app.presentation.utils.collectIsScreenReaderEnabledAsState
import kotlin.math.round
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumVoteDistribution(
    stats: AlbumStats,
    modifier: Modifier = Modifier,
) {
    val isScreenReaderOn by collectIsScreenReaderEnabledAsState()

    fun rounded(value: Int) = round(value * 100 / stats.summedVotes.toFloat()).toInt()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.small),
        verticalAlignment = Alignment.Bottom,
    ) {
        for ((index, value) in stats.votesList.withIndex()) {
            val rating = index + 1

            Tooltip(
                text = "${rounded(value)}%",
                enableUserInput = !isScreenReaderOn,
                showTooltipByShortPress = true,
            ) {
                val description = stringResource(
                    Res.string.album_stats_vote_distribution_accessibility,
                    rounded(value),
                    rating,
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clearAndSetSemantics {
                            contentDescription = description
                        },
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight(
                                value / stats.summedVotes.toFloat() / stats.maxValue,
                            )
                            .width(25.dp)
                            .background(MaterialTheme.colorScheme.primary),
                    )
                    Text(text = "$rating")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumVoteDistributionPreview() {
    AppTheme {
        AlbumVoteDistribution(
            stats = PreviewData.stats,
            modifier = Modifier
                .padding(all = Paddings.medium)
                .height(100.dp),
        )
    }
}
