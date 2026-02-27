package com.albumsgenerator.app.presentation.screens.year

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
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.common.components.AppBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.top.TopState
import com.albumsgenerator.app.presentation.screens.year.components.YearContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearScreen(
    data: Route.Year,
    navigateTo: (Route) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: YearViewModel =
        assistedMetroViewModel<YearViewModel, YearViewModel.Factory> { create(data) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val loadingState by state.rememberLoadingState()

    val title = data.year

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
                    navigateTo(
                        Route.Web(
                            url = "${Constants.WEBSITE_URL}/years/$title",
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
            label = "YearScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    YearContent(
                        state = TopState.EMPTY,
                        navigateToAlbum = {},
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    val state = state.contentOrNull() ?: return@Crossfade
                    YearContent(
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
