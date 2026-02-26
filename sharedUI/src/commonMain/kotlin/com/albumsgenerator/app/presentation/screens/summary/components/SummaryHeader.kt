package com.albumsgenerator.app.presentation.screens.summary.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.summary_albums_average
import albumsgenerator.sharedui.generated.resources.summary_albums_complete
import albumsgenerator.sharedui.generated.resources.summary_albums_rated
import albumsgenerator.sharedui.generated.resources.summary_albums_remaining
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.presentation.common.components.A11yRow
import com.albumsgenerator.app.presentation.common.components.StatDisplay
import com.albumsgenerator.app.presentation.screens.summary.SummaryScreenState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.format
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun SummaryHeader(
    summary: SummaryScreenState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(all = Paddings.large),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            A11yRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Paddings.small),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                StatDisplay(
                    label = stringResource(Res.string.summary_albums_rated),
                    value = "${summary.albumsRated}",
                    modifier = Modifier
                        .weight(1f),
                    isLoading = isLoading,
                )

                StatDisplay(
                    label = stringResource(Res.string.summary_albums_average),
                    value = summary.averageRating.format(),
                    modifier = Modifier
                        .weight(1f),
                    isLoading = isLoading,
                )

                StatDisplay(
                    label = stringResource(Res.string.summary_albums_complete),
                    value = "${(summary.percentageComplete * 100).toInt()}%",
                    modifier = Modifier
                        .weight(1f),
                    isLoading = isLoading,
                )
            }

            LinearProgressIndicator(
                progress = { summary.percentageComplete },
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(visible = isLoading),
            )

            Text(
                text = stringResource(
                    Res.string.summary_albums_remaining,
                    Constants.TOTAL_ALBUMS_COUNT - summary.albumsRated,
                ),
                modifier = Modifier
                    .placeholder(visible = isLoading),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
private fun SummaryHeaderPreview() {
    AppTheme {
        SummaryHeader(
            summary = SummaryScreenState(
                albumsRated = 545,
                averageRating = 3.0f,
                percentageComplete = 0.5f,
            ),
        )
    }
}
