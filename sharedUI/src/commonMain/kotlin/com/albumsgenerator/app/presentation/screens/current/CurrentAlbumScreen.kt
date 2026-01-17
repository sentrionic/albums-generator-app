package com.albumsgenerator.app.presentation.screens.current

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.current_album_title
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.presentation.common.components.BottomBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.current.components.CurrentAlbumContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentAlbumScreen(
    navigateTo: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CurrentAlbumViewModel = metroViewModel(),
) {
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val service by viewModel.streamingService.collectAsStateWithLifecycle()

    val title = stringResource(Res.string.current_album_title)
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
                current = Route.CurrentAlbum,
                onSelect = navigateTo,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Crossfade(
            targetState = loadingState,
            modifier = Modifier
                .padding(innerPadding),
            label = "CurrentAlbumScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    CurrentAlbumContent(
                        state = CurrentAlbumState(
                            project = PreviewData.project,
                            previousAlbums = emptyList(),
                        ),
                        service = StreamingServices.SPOTIFY,
                        showMessage = { },
                        navigateTo = {},
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    val state = state.contentOrNull() ?: return@Crossfade
                    CurrentAlbumContent(
                        state = state,
                        service = service,
                        showMessage = { message ->
                            scope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        },
                        navigateTo = navigateTo,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()),
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
