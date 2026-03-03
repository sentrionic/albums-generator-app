package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun StylesTab(
    byStyles: ImmutableList<JourneyState.ItemWithAlbums>,
    modifier: Modifier = Modifier,
) {
    ExpandableLazyList(
        itemsWithAlbums = byStyles,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun StylesTabPreview() {
    AppTheme {
        StylesTab(
            byStyles = emptyImmutableList(),
        )
    }
}
