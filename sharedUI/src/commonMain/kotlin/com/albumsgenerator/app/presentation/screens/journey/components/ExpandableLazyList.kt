package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.core.immutableFilter
import com.albumsgenerator.app.presentation.common.components.SearchField
import com.albumsgenerator.app.presentation.common.modifiers.listItemSemantics
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.eygraber.compose.placeholder.material3.placeholder
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ExpandableLazyList(
    itemsWithAlbums: ImmutableList<JourneyState.ItemWithAlbums>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val scrollState = rememberLazyListState()

    val query = rememberTextFieldState()

    val filteredItems = remember(query.text, itemsWithAlbums) {
        if (query.text.isNotEmpty()) {
            itemsWithAlbums.immutableFilter {
                it.label.contains(query.text, ignoreCase = true)
            }
        } else {
            itemsWithAlbums
        }
    }

    var scrollToTop by rememberSaveable(query.text) {
        mutableStateOf(true)
    }

    LaunchedEffect(query.text) {
        if (scrollToTop && scrollState.firstVisibleItemIndex != 0) {
            scrollState.scrollToItem(0)
        }
        scrollToTop = false
    }

    Column(modifier = modifier) {
        SearchField(
            query = query.text.toString(),
            setQuery = query::setTextAndPlaceCursorAtEnd,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Paddings.medium)
                .placeholder(visible = isLoading),
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(all = Paddings.medium),
                verticalArrangement = Arrangement.spacedBy(Paddings.medium),
            ) {
                items(filteredItems, key = { it.label }) { style ->
                    ToggleableSectionCard(
                        title = style.label,
                        average = style.average,
                        albumsCount = style.albums.size,
                    ) {
                        for ((index, album) in style.albums.withIndex()) {
                            Text(
                                text = "${album.name} - ${album.artist}",
                                modifier = Modifier
                                    .listItemSemantics(index),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExpandableLazyListPreview() {
    AppTheme {
        ExpandableLazyList(
            itemsWithAlbums = emptyImmutableList(),
        )
    }
}
