package com.albumsgenerator.app.presentation.screens.album

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.reviews
import albumsgenerator.sharedui.generated.resources.spoiler_mode_warning
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.common.components.AppBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.album.components.AlbumContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(
    data: Route.Album,
    navigateTo: (Route) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumViewModel =
        assistedMetroViewModel<AlbumViewModel, AlbumViewModel.Factory> { create(data) },
) {
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val loadingState by state.rememberLoadingState()
    val snackbarHostState = remember { SnackbarHostState() }

    val title = data.albumName
    val reviewsTitle = stringResource(Res.string.reviews)

    Scaffold(
        modifier = modifier
            .semantics {
                paneTitle = title
            },
        topBar = {
            AppBar(
                title = title,
                onBack = onBack,
                onOpenWeb = {
                    val globalReviewsUrl = state.contentOrNull()?.stats?.globalReviewsUrl

                    if (globalReviewsUrl != null) {
                        navigateTo(
                            Route.Web(
                                url = globalReviewsUrl,
                                title = reviewsTitle,
                            ),
                        )
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                getString(Res.string.spoiler_mode_warning),
                            )
                        }
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { padding ->
        Crossfade(
            targetState = loadingState,
            modifier = Modifier
                .padding(padding),
            label = "AlbumScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    AlbumContent(
                        state = AlbumState(
                            history = PreviewData.history,
                            stats = PreviewData.stats,
                        ),
                        showMessage = {},
                        navigateTo = {},
                        modifier = Modifier
                            .padding(all = Paddings.large),
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    AlbumContent(
                        state = state.contentOrNull() ?: return@Crossfade,
                        showMessage = {
                            scope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                        navigateTo = navigateTo,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(all = Paddings.large),
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
