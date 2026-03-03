package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun OutlierListItem(
    history: History,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val isAccessibilitySize = LocalDensity.current.fontScale > 1.5f

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Paddings.large),
    ) {
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (isAccessibilitySize) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.End,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Paddings.extraLarge),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${history.rating}",
                        modifier = Modifier
                            .weight(1f, fill = false),
                    )

                    Text(
                        text = "${history.globalRating}",
                        modifier = Modifier
                            .weight(1f, fill = false),
                    )
                }

                val diff = history.ratingDiff
                Text(
                    text = if (diff > 0) {
                        "+"
                    } else {
                        ""
                    } + "$diff",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(Paddings.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${history.rating}",
                    modifier = Modifier
                        .weight(1f),
                )

                Text(
                    text = "${history.globalRating}",
                    modifier = Modifier
                        .weight(1f),
                )

                val diff = history.ratingDiff
                Text(
                    text = if (diff > 0) {
                        "+"
                    } else {
                        ""
                    } + "$diff",
                    modifier = Modifier
                        .weight(1f),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Preview
@Composable
private fun OutlierListItemPreview() {
    AppTheme {
        OutlierListItem(
            history = PreviewData.history,
        )
    }
}

@Preview(fontScale = 1.6f)
@Composable
private fun OutlierListItemIncreasedFontSizePreview() {
    AppTheme {
        OutlierListItem(
            history = PreviewData.history,
        )
    }
}
