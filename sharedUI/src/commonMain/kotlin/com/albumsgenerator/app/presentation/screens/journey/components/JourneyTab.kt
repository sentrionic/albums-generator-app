package com.albumsgenerator.app.presentation.screens.journey.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.your_journey_by_decade
import albumsgenerator.sharedui.generated.resources.your_journey_by_genre
import albumsgenerator.sharedui.generated.resources.your_journey_by_origin
import albumsgenerator.sharedui.generated.resources.your_journey_decade
import albumsgenerator.sharedui.generated.resources.your_journey_genre
import albumsgenerator.sharedui.generated.resources.your_journey_item_accessibility
import albumsgenerator.sharedui.generated.resources.your_journey_listening_history
import albumsgenerator.sharedui.generated.resources.your_journey_origin
import albumsgenerator.sharedui.generated.resources.your_journey_outliers_above_average
import albumsgenerator.sharedui.generated.resources.your_journey_outliers_below_average
import albumsgenerator.sharedui.generated.resources.your_journey_outliers_description
import albumsgenerator.sharedui.generated.resources.your_journey_outliers_item_accessibility
import albumsgenerator.sharedui.generated.resources.your_journey_outliers_title
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
import com.albumsgenerator.app.domain.core.immutableMap
import com.albumsgenerator.app.presentation.common.modifiers.listItemSemantics
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.format
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

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
        JourneyItems(
            state = state,
            isLoading = isLoading,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.small),
        ) {
            Text(
                text = stringResource(Res.string.your_journey_outliers_title),
                modifier = Modifier
                    .placeholder(visible = isLoading),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = stringResource(Res.string.your_journey_outliers_description),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        OutlierItems(
            state = state,
            isLoading = isLoading,
        )
    }
}

@Composable
private fun JourneyItems(
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
            text = stringResource(Res.string.your_journey_listening_history),
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(visible = isLoading),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
        )

        JourneySectionCard(
            title = stringResource(Res.string.your_journey_by_decade),
            label = stringResource(Res.string.your_journey_decade),
            listSize = state.byDecade.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.byDecade.withIndex()) {
                val stateLabel =
                    stringResource(
                        Res.string.your_journey_item_accessibility,
                        item.label,
                        item.albumsCount,
                        item.average.format(),
                        item.global.format(),
                    )
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                AlbumTableItem(
                    item = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription = stateLabel
                        },
                    isLoading = isLoading,
                )
            }
        }

        JourneySectionCard(
            title = stringResource(Res.string.your_journey_by_genre),
            label = stringResource(Res.string.your_journey_genre),
            listSize = state.byGenre.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.byGenre.withIndex()) {
                val stateLabel =
                    stringResource(
                        Res.string.your_journey_item_accessibility,
                        item.label,
                        item.albumsCount,
                        item.average.format(),
                        item.global.format(),
                    )
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                AlbumTableItem(
                    item = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription = stateLabel
                        },
                    isLoading = isLoading,
                )
            }
        }

        JourneySectionCard(
            title = stringResource(Res.string.your_journey_by_origin),
            label = stringResource(Res.string.your_journey_origin),
            listSize = state.byOrigin.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.byOrigin.withIndex()) {
                val stateLabel =
                    stringResource(
                        Res.string.your_journey_item_accessibility,
                        item.label,
                        item.albumsCount,
                        item.average.format(),
                        item.global.format(),
                    )
                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                AlbumTableItem(
                    item = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription = stateLabel
                        },
                    isLoading = isLoading,
                )
            }
        }
    }
}

@Composable
private fun OutlierItems(
    state: JourneyState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.large),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlierSection(
            title = stringResource(Res.string.your_journey_outliers_above_average),
            albumsCount = state.aboveAverageOutliers.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.aboveAverageOutliers.withIndex()) {
                val stateLabel = stringResource(
                    Res.string.your_journey_outliers_item_accessibility,
                    item.album.name,
                    item.album.artist,
                    item.rating.orEmpty(),
                    item.globalRating,
                    item.ratingDiff.format(),
                )

                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                OutlierListItem(
                    history = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription = stateLabel
                        },
                    isLoading = isLoading,
                )
            }
        }

        OutlierSection(
            title = stringResource(Res.string.your_journey_outliers_below_average),
            albumsCount = state.belowAverageOutliers.size,
            isLoading = isLoading,
        ) {
            for ((index, item) in state.belowAverageOutliers.withIndex()) {
                val stateLabel = stringResource(
                    Res.string.your_journey_outliers_item_accessibility,
                    item.album.name,
                    item.album.artist,
                    item.rating.orEmpty(),
                    item.globalRating,
                    item.ratingDiff.format(),
                )

                HorizontalDivider(modifier = Modifier.placeholder(visible = isLoading))
                OutlierListItem(
                    history = item,
                    modifier = Modifier
                        .listItemSemantics(index)
                        .clearAndSetSemantics {
                            stateDescription = stateLabel
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
        (0..3).immutableMap {
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
            state = JourneyState.EMPTY.copy(
                byDecade = items,
                byGenre = items,
                byOrigin = items,
            ),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(all = Paddings.medium),
        )
    }
}
