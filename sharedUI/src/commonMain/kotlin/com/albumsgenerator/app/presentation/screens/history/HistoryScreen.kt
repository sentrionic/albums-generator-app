package com.albumsgenerator.app.presentation.screens.history

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.history_title
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.common.components.BottomBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.history.components.HistoryContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navigateTo: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = assistedMetroViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val title = stringResource(Res.string.history_title)
    val loadingState by state.rememberLoadingState()

    Scaffold(
        modifier = modifier
            .semantics {
                paneTitle = title
            },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
            )
        },
        bottomBar = {
            BottomBar(
                current = Route.History,
                onSelect = navigateTo,
            )
        },
    ) { innerPadding ->
        Crossfade(
            targetState = loadingState,
            modifier = Modifier
                .padding(innerPadding),
            label = "HistoryScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    val album = PreviewData.history.album
                    HistoryContent(
                        state = HistoryScreenState(
                            filteredHistories = (0..<20).map {
                                PreviewData.history.copy(album = album.copy(uuid = "$it"))
                            },
                            historiesCount = 20,
                            historiesWithRatingCount = 20,
                            genres = PreviewData.genres,
                        ),
                        query = TextFieldValue(),
                        sendEvent = {},
                        navigateToAlbum = {},
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    val state = state.contentOrNull() ?: return@Crossfade
                    HistoryContent(
                        state = state,
                        query = viewModel.query,
                        sendEvent = viewModel::onEvent,
                        navigateToAlbum = { album ->
                            navigateTo(Route.Album(albumId = album.uuid, albumName = album.name))
                        },
                    )
                }

                is DataState.Error -> {
                    ErrorCard(
                        message = stringResource(result.message),
                        modifier = Modifier
                            .padding(all = Paddings.large),
                    )
                }
            }
        }
    }
}
