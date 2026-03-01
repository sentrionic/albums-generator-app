package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.modifiers.listItemSemantics
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.format
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun JourneyTab(
    state: JourneyState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.large),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Listening History",
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(visible = isLoading),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
        )

        JourneySectionCard(
            title = "By Decade",
            label = "Decade",
            listSize = state.byDecade.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.byDecade.withIndex()) {
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                AlbumTableItem(
                    item = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription =
                                "${item.label}. ${item.albumsCount} albums. Your average: ${item.average.format()}. Global: ${item.global.format()}"
                        },
                    isLoading = isLoading,
                )
            }
        }

        JourneySectionCard(
            title = "By Genre",
            label = "Genre",
            listSize = state.byGenre.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.byGenre.withIndex()) {
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                AlbumTableItem(
                    item = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription =
                                "${item.label}. ${item.albumsCount} albums. Your average: ${item.average.format()}. Global: ${item.global.format()}"
                        },
                    isLoading = isLoading,
                )
            }
        }

        JourneySectionCard(
            title = "By Origin",
            label = "Origin",
            listSize = state.byOrigin.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.byOrigin.withIndex()) {
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                AlbumTableItem(
                    item = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription =
                                "${item.label}. ${item.albumsCount} albums. Your average: ${item.average.format()}. Global: ${item.global.format()}"
                        },
                    isLoading = isLoading,
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Paddings.small),
        ) {
            Text(
                text = "Outliers",
                modifier = Modifier
                    .placeholder(visible = isLoading),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium,
            )

            @Suppress("ktlint:standard:max-line-length")
            Text(
                text = "Albums where your rating differs significantly from the average rating for that album.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        OutlierSection(
            title = "Above Average",
            albumsCount = state.aboveAverageOutliers.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.aboveAverageOutliers.withIndex()) {
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                OutlierListItem(
                    history = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription =
                                "${item.album.name} by ${item.album.artist}. Your rating: ${item.rating}. Global average: ${item.globalRating}. Difference: ${item.ratingDiff.format()}"
                        },
                    isLoading = isLoading,
                )
            }
        }

        OutlierSection(
            title = "Below Average",
            albumsCount = state.belowAverageOutliers.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.belowAverageOutliers.withIndex()) {
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                OutlierListItem(
                    history = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription =
                                "${item.album.name} by ${item.album.artist}. Your rating: ${item.rating}. Global average: ${item.globalRating}. Difference: ${item.ratingDiff.format()}"
                        },
                    isLoading = isLoading,
                )
            }
        }
    }
}

@Preview
@Composable
private fun JourneyTabPreview() {
    val items = remember {
        (0..3).map {
            JourneyState.Item(
                label = "Label $it",
                albumsCount = it,
                average = it.toFloat(),
                global = it.toFloat(),
            )
        }
    }

    AppTheme {
        JourneyTab(
            state = JourneyState(
                byDecade = items,
                byGenre = items,
                byOrigin = items,
                byStyles = emptyList(),
                byYear = emptyList(),
                aboveAverageOutliers = emptyList(),
                belowAverageOutliers = emptyList(),
            ),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(all = Paddings.medium),
        )
    }
}
