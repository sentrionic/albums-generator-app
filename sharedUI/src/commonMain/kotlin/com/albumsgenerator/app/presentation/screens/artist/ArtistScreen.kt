package com.albumsgenerator.app.presentation.screens.artist

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
import com.albumsgenerator.app.presentation.screens.artist.components.ArtistContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    data: Route.Artist,
    navigateTo: (Route) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArtistViewModel =
        assistedMetroViewModel<ArtistViewModel, ArtistViewModel.Factory> { create(data) },
) {
    val stateFlow by viewModel.state.collectAsStateWithLifecycle()

    val loadingState by stateFlow.rememberLoadingState()

    val title = data.artist

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
                        .replace(" ", "-") // The Beach Boys
                        .replace("'", "") // The B-52's
                        .replace(".", "") // B.B. King
                        .replace(",", "") // Crosby, Stills & Nash
                        .replace("\"", "") // Bonnie "Prince" Billy
                        .replace("ö", "o") // Björk
                        .replace("ó", "o") // Sigur Rós
                        .replace("&", "and") // Simon & Garfunkel
                        .replace("/", "") // AC/DC
                    navigateTo(
                        Route.Web(
                            url = "${BuildConfig.WEBSITE_URL}/artists/$slug",
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
            label = "ArtistScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    ArtistContent(
                        state = ArtistState(
                            albums = listOf(PreviewData.album),
                            albumStats = emptyList(),
                            spoilerFree = true,
                        ),
                        navigateToAlbum = {},
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    val state = stateFlow.contentOrNull() ?: return@Crossfade
                    ArtistContent(
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
