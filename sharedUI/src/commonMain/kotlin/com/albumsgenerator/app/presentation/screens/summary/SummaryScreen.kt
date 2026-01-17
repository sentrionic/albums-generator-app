package com.albumsgenerator.app.presentation.screens.summary

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.destination_summary
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.common.components.BottomBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.summary.components.SummaryContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    navigateTo: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SummaryViewModel = metroViewModel(),
) {
    val summary by viewModel.summary.collectAsStateWithLifecycle()

    val title = stringResource(Res.string.destination_summary)
    val loadingState by summary.rememberLoadingState()

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
                current = Route.Summary,
                onSelect = navigateTo,
            )
        },
    ) { innerPadding ->
        Crossfade(
            targetState = loadingState,
            modifier = Modifier
                .padding(innerPadding),
            label = "SummaryScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    val loadingAlbums = remember {
                        (0..<20).map {
                            PreviewData.album.copy(uuid = "Album #$it")
                        }
                    }
                    SummaryContent(
                        summary = SummaryScreenState(
                            albumsRated = 123,
                            averageRating = 3.0f,
                            percentageComplete = 0.25f,
                            fiveStarAlbums = loadingAlbums,
                            oneStarAlbums = loadingAlbums,
                        ),
                        navigateTo = {},
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    val state = summary.contentOrNull() ?: return@Crossfade
                    SummaryContent(
                        summary = state,
                        navigateTo = navigateTo,
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
