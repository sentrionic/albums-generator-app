package com.albumsgenerator.app.presentation.screens.history.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_rating_accessibility
import albumsgenerator.sharedui.generated.resources.album_rating_global_accessibility
import albumsgenerator.sharedui.generated.resources.history_item_number_accessibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.presentation.common.components.A11yRow
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.albumsgenerator.app.presentation.utils.formatLocalDate
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistoryListItem(
    history: History,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Paddings.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Paddings.small),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = formatLocalDate(history.generatedAtDate),
                    modifier = Modifier
                        .placeholder(visible = isLoading),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                )

                val description =
                    stringResource(Res.string.history_item_number_accessibility, history.index)
                Text(
                    text = "${history.index}",
                    modifier = Modifier
                        .placeholder(visible = isLoading)
                        .semantics {
                            contentDescription = description
                        },
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = history.album.name,
                    modifier = Modifier
                        .placeholder(visible = isLoading),
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = history.album.artist,
                    modifier = Modifier
                        .placeholder(visible = isLoading),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            A11yRow(
                modifier = Modifier
                    .weight(0.5f),
                horizontalArrangement = Arrangement.spacedBy(Paddings.small),
                horizontalAlignment = Alignment.End,
            ) {
                val ratingOrEmpty = remember { history.rating.takeIf { history.hasRating } ?: "" }
                val description =
                    stringResource(Res.string.album_rating_accessibility, ratingOrEmpty)
                Text(
                    text = ratingOrEmpty,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(visible = isLoading)
                        .semantics {
                            if (ratingOrEmpty.isNotEmpty()) {
                                contentDescription = description
                            }
                        },
                    style = MaterialTheme.typography.titleMedium,
                )

                val globalRatingOrEmpty = remember {
                    "${history.globalRating.takeIf { history.hasRating } ?: ""}"
                }

                val averageRatingDescription = stringResource(
                    Res.string.album_rating_global_accessibility,
                    history.globalRating,
                )
                Text(
                    text = globalRatingOrEmpty,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(visible = isLoading)
                        .semantics {
                            if (globalRatingOrEmpty.isNotEmpty()) {
                                contentDescription = averageRatingDescription
                            }
                        },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryListItemPreview() {
    AppTheme {
        HistoryListItem(
            history = PreviewData.history,
            modifier = Modifier
                .padding(all = Paddings.medium),
        )
    }
}
