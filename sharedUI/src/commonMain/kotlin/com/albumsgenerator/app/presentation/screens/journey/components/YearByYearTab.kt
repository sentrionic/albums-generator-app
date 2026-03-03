package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun YearByYearTab(
    byYears: ImmutableList<JourneyState.ItemWithAlbums>,
    modifier: Modifier = Modifier,
) {
    ExpandableLazyList(
        itemsWithAlbums = byYears,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun YearByYearTabPreview() {
    AppTheme {
        YearByYearTab(
            byYears = emptyImmutableList(),
        )
    }
}
