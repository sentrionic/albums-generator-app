package com.albumsgenerator.app.presentation.screens.journey

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
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.common.components.AppBar
import com.albumsgenerator.app.presentation.common.components.ErrorCard
import com.albumsgenerator.app.presentation.screens.journey.components.JourneyContent
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JourneyViewModel = assistedMetroViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val loadingState by state.rememberLoadingState()

    val title = "Your album journey"

    Scaffold(
        modifier = modifier
            .semantics {
                paneTitle = title
            },
        topBar = {
            AppBar(
                title = title,
                onBack = onBack,
            )
        },
    ) { padding ->
        Crossfade(
            targetState = loadingState,
            modifier = Modifier
                .padding(padding),
            label = "JourneyScreenCrossFade",
        ) { result ->
            when (result) {
                is DataState.Loading -> {
                    val placeholderItems = List(10) {
                        JourneyState.Item(
                            label = "Label",
                            albumsCount = 10,
                            average = 0.0f,
                            global = 0.0f,
                        )
                    }

                    JourneyContent(
                        state = JourneyState.EMPTY.copy(
                            byDecade = placeholderItems,
                            byGenre = placeholderItems,
                            byOrigin = placeholderItems,
                        ),
                        isLoading = true,
                    )
                }

                is DataState.Success -> {
                    JourneyContent(
                        state = state.contentOrNull() ?: return@Crossfade,
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
