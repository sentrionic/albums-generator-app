package com.albumsgenerator.app.presentation.screens.stats.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_stats_votes
import albumsgenerator.sharedui.generated.resources.statistics_average_rating
import albumsgenerator.sharedui.generated.resources.statistics_total_albums
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.BuildConfig
import com.albumsgenerator.app.presentation.common.components.A11yRow
import com.albumsgenerator.app.presentation.common.components.StatDisplay
import com.albumsgenerator.app.presentation.screens.stats.StatsScreenState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.format
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatsHeader(
    state: StatsScreenState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Card(modifier = modifier) {
        A11yRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Paddings.large),
            horizontalArrangement = Arrangement.spacedBy(Paddings.small),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            StatDisplay(
                label = stringResource(Res.string.statistics_total_albums),
                value = BuildConfig.TOTAL_ALBUMS_COUNT.toString(),
                modifier = Modifier
                    .weight(1f),
                isLoading = isLoading,
            )

            StatDisplay(
                label = stringResource(Res.string.album_stats_votes),
                value = state.votes.toString(),
                modifier = Modifier
                    .weight(1f),
                isLoading = isLoading,
            )

            StatDisplay(
                label = stringResource(Res.string.statistics_average_rating),
                value = state.averageRating.format(),
                modifier = Modifier
                    .weight(1f),
                isLoading = isLoading,
            )
        }
    }
}

@Preview
@Composable
private fun StatsHeaderPreview() {
    AppTheme {
        StatsHeader(
            state = StatsScreenState(
                votes = 12345678,
                averageRating = 3.0f,
            ),
        )
    }
}
