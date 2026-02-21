package com.albumsgenerator.app.presentation.screens.genre

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.albumsgenerator.app.BuildConfig
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.common.components.AppBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.genre.components.GenreContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.albumsgenerator.app.presentation.utils.capitalize
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreScreen(
    data: Route.Genre,
    navigateTo: (Route) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenreViewModel =
        assistedMetroViewModel<GenreViewModel, GenreViewModel.Factory> { create(data) },
) {
    val stateFlow by viewModel.state.collectAsStateWithLifecycle()

    val loadingState by stateFlow.rememberLoadingState()

    val title = data.genre.capitalize()

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
                    val slug = title
                        .lowercase()
                        .replace(" ", "-") // Hip-Hop
                    navigateTo(
                        Route.Web(
                            url = "${BuildConfig.WEBSITE_URL}/genres/$slug",
                            title = title,
                        ),
                    )
                },
            )
        },
    ) { padding ->
        Crossfade(
            targetState = loadingState,
            modifier = Modifier
                .padding(padding),
            label = "GenreScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    GenreContent(
                        state = GenreState(
                            histories = listOf(PreviewData.history),
                            stats = listOf(PreviewData.stats),
                            spoilerFree = true,
                        ),
                        navigateToAlbum = {},
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    val state = stateFlow.contentOrNull() ?: return@Crossfade
                    GenreContent(
                        state = state,
                        navigateToAlbum = navigateTo,
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
