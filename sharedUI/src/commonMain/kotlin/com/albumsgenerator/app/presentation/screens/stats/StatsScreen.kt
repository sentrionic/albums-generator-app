package com.albumsgenerator.app.presentation.screens.stats

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.statistics_title
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
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.common.components.BottomBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.stats.components.StatsContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    navigateTo: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StatsViewModel = metroViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val title = stringResource(Res.string.statistics_title)

    Scaffold(
        modifier = modifier,
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
                current = Route.Stats,
                onSelect = navigateTo,
            )
        },
    ) { innerPadding ->
        Crossfade(
            targetState = state,
            modifier = Modifier
                .padding(innerPadding),
            label = "StatsScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    val loadingAlbums = remember {
                        (0..<20).map {
                            PreviewData.stats.copy(name = "Album #$it")
                        }
                    }
                    StatsContent(
                        state = StatsScreenState(
                            topAlbums = loadingAlbums,
                            bottomAlbums = loadingAlbums,
                            mostControversial = loadingAlbums,
                            leastControversial = loadingAlbums,
                            votes = 12345678,
                            averageRating = 3.0f,
                        ),
                        navigateTo = {},
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    StatsContent(
                        state = result.content,
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
