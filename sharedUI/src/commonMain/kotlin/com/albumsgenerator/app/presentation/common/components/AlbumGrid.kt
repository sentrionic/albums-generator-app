package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun AlbumGrid(
    header: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: LazyGridScope.() -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(all = Paddings.large),
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) },
            contentType = "header",
        ) {
            Text(
                text = header,
                modifier = Modifier
                    .placeholder(isLoading),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        content()
    }
}
