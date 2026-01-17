package com.albumsgenerator.app.presentation.screens.history.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_navigate_accessibility
import albumsgenerator.sharedui.generated.resources.history_subtitle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.BuildConfig
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.presentation.screens.history.HistoryScreenEvents
import com.albumsgenerator.app.presentation.screens.history.HistoryScreenState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    state: HistoryScreenState,
    query: TextFieldValue,
    sendEvent: (HistoryScreenEvents) -> Unit,
    navigateToAlbum: (Album) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    var scrollToTop by rememberSaveable(query, state.genre, state.rating) {
        mutableStateOf(true)
    }

    LaunchedEffect(query, state.genre, state.rating) {
        if (scrollToTop && scrollState.firstVisibleItemIndex != 0) {
            scrollState.scrollToItem(0)
        }
        scrollToTop = false
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(all = Paddings.large),
            verticalArrangement = Arrangement.spacedBy(Paddings.small),
        ) {
            item(
                key = "HistoryContentHeader",
                contentType = "header",
            ) {
                HistoryContentHeader(
                    query = query,
                    rating = state.rating,
                    genres = state.genres,
                    genre = state.genre,
                    sendEvent = sendEvent,
                    isLoading = isLoading,
                )
            }

            item(
                key = "subtitle",
                contentType = "header",
            ) {
                Text(
                    text = stringResource(
                        Res.string.history_subtitle,
                        state.historiesWithRatingCount,
                        state.historiesCount,
                        BuildConfig.TOTAL_ALBUMS_COUNT,
                    ),
                    modifier = Modifier
                        .placeholder(visible = isLoading),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            items(
                items = state.filteredHistories,
                key = { it.album.uuid },
                contentType = { "history-item" },
            ) { history ->
                HistoryListItem(
                    history = history,
                    modifier = Modifier
                        .animateItem()
                        .clip(MaterialTheme.shapes.medium)
                        .clickable(
                            enabled = !isLoading,
                            onClickLabel = stringResource(Res.string.album_navigate_accessibility),
                            onClick = {
                                navigateToAlbum(history.album)
                            },
                        ),
                    isLoading = isLoading,
                )
            }
        }

        AnimatedVisibility(
            visible = !scrollState.isScrollingUp(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = Paddings.large),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) {
        mutableStateOf(firstVisibleItemIndex)
    }

    var previousScrollOffset by remember(this) {
        mutableStateOf(firstVisibleItemScrollOffset)
    }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Preview(showBackground = true)
@Composable
private fun HistoryContentPreview() {
    AppTheme {
        HistoryContent(
            state = HistoryScreenState(
                filteredHistories = listOf(PreviewData.history),
                historiesCount = 1,
                historiesWithRatingCount = 1,
                genres = PreviewData.genres,
            ),
            query = TextFieldValue(),
            sendEvent = {},
            navigateToAlbum = {},
        )
    }
}
