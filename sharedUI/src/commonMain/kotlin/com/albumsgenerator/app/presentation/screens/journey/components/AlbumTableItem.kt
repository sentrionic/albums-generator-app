package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.format
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun AlbumTableItem(
    item: JourneyState.Item,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Paddings.small),
    ) {
        Text(
            text = item.label,
            modifier = Modifier
                .weight(1f)
                .placeholder(isLoading),
        )

        Text(
            text = item.albumsCount.toString(),
            modifier = Modifier
                .weight(1f)
                .placeholder(isLoading),
        )

        Text(
            text = item.average.format(),
            modifier = Modifier
                .weight(1f)
                .placeholder(isLoading),
        )

        Text(
            text = item.global.format(),
            modifier = Modifier
                .weight(1f)
                .placeholder(isLoading),
        )
    }
}

@Preview
@Composable
private fun AlbumTableItemPreview() {
    AppTheme {
        AlbumTableItem(
            item = JourneyState.Item(
                label = "Label",
                albumsCount = 1,
                average = 3.0f,
                global = 3.1f,
            ),
        )
    }
}
