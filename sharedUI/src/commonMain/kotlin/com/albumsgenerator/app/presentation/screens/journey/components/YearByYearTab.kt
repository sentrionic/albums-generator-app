package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.modifiers.listItemSemantics
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings

@Composable
fun YearByYearTab(
    byYear: List<JourneyState.ItemWithAlbums>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = Paddings.medium),
        verticalArrangement = Arrangement.spacedBy(Paddings.large),
    ) {
        items(byYear, key = { it.label }) { style ->
            ToggleableSectionCard(
                title = style.label,
                albumsCount = style.albums.size,
            ) {
                for ((index, album) in style.albums.withIndex()) {
                    Text(
                        text = "${album.name} - ${album.artist}",
                        modifier = Modifier
                            .listItemSemantics(index),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun YearByYearTabPreview() {
    AppTheme {
        YearByYearTab(
            byYear = emptyList(),
        )
    }
}
