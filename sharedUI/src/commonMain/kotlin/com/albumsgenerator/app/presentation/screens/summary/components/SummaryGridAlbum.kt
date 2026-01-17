package com.albumsgenerator.app.presentation.screens.summary.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.presentation.common.components.NetworkImage
import com.albumsgenerator.app.presentation.common.components.Tooltip
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun SummaryGridItem(
    album: Album,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Card(
        modifier = modifier,
    ) {
        Tooltip(text = album.name) {
            NetworkImage(
                url = album.images.getOrNull(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .placeholder(visible = isLoading),
                contentDescription = album.name,
            )
        }
    }
}
