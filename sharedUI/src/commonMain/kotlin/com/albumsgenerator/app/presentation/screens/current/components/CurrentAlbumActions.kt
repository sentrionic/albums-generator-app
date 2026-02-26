package com.albumsgenerator.app.presentation.screens.current.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.action_error
import albumsgenerator.sharedui.generated.resources.action_open_info
import albumsgenerator.sharedui.generated.resources.action_open_reviews
import albumsgenerator.sharedui.generated.resources.action_open_service
import albumsgenerator.sharedui.generated.resources.action_open_web
import albumsgenerator.sharedui.generated.resources.action_open_wikipedia
import albumsgenerator.sharedui.generated.resources.ic_external_link
import albumsgenerator.sharedui.generated.resources.ic_info
import albumsgenerator.sharedui.generated.resources.ic_reviews
import albumsgenerator.sharedui.generated.resources.ic_wikipedia
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.presentation.common.components.Tooltip
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.current.CurrentAlbumState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.albumsgenerator.app.presentation.utils.collectIsScreenReaderEnabledAsState
import com.eygraber.compose.placeholder.material3.placeholder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentAlbumActions(
    state: CurrentAlbumState,
    service: StreamingServices,
    showMessage: (String) -> Unit,
    showWebRoute: (Route.Web) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val uriHandler = LocalUriHandler.current

    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val isScreenReaderOn by collectIsScreenReaderEnabledAsState()

    val album = remember(state) { state.project.currentAlbum }

    fun openStreamingService(uri: String) {
        try {
            uriHandler.openUri(uri)
        } catch (e: Exception) {
            Logger.e(e) { "Could not open the link" }
            scope.launch {
                showMessage(getString(Res.string.action_error))
            }
        }
    }

    if (showBottomSheet) {
        CurrentAlbumMoreInfoSheet(
            album = album,
            previousAlbums = state.previousAlbums,
            onOpenUri = ::openStreamingService,
            onDismiss = {
                showBottomSheet = false
            },
        )
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = Paddings.medium,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalArrangement = Arrangement.Center,
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        CurrentIconButton(
            iconRes = Res.drawable.ic_wikipedia,
            onClick = {
                showWebRoute(Route.Web(url = album.responsiveWikipediaUrl, title = "Wikipedia"))
            },
            contentDescription = stringResource(Res.string.action_open_wikipedia),
            enabled = !isLoading,
            modifier = Modifier
                .placeholder(
                    visible = isLoading,
                    shape = CircleShape,
                ),
        )

        CurrentIconButton(
            iconRes = Res.drawable.ic_external_link,
            onClick = {
                showWebRoute(
                    Route.Web(url = "${Constants.WEBSITE_URL}/${state.project.name}", title = ""),
                )
            },
            contentDescription = stringResource(Res.string.action_open_web),
            enabled = !isLoading,
            modifier = Modifier
                .placeholder(
                    visible = isLoading,
                    shape = CircleShape,
                ),
        )

        Tooltip(
            text = stringResource(Res.string.action_open_service, service.label),
            enableUserInput = !isScreenReaderOn,
        ) {
            FilledTonalIconButton(
                onClick = {
                    openStreamingService("${service.url}${album.serviceUrl(service)}")
                },
                modifier = Modifier
                    .size(60.dp)
                    .placeholder(
                        visible = isLoading,
                        shape = CircleShape,
                    ),
                enabled = !isLoading,
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    modifier = Modifier
                        .size(48.dp),
                    contentDescription = stringResource(
                        Res.string.action_open_service,
                        service.name,
                    ),
                )
            }
        }

        CurrentIconButton(
            iconRes = Res.drawable.ic_reviews,
            onClick = {
                showWebRoute(Route.Web(url = album.globalReviewsUrl, title = "Reviews"))
            },
            contentDescription = stringResource(Res.string.action_open_reviews),
            enabled = !isLoading,
            modifier = Modifier
                .placeholder(
                    visible = isLoading,
                    shape = CircleShape,
                ),
        )

        CurrentIconButton(
            iconRes = Res.drawable.ic_info,
            onClick = {
                showBottomSheet = true
            },
            contentDescription = stringResource(Res.string.action_open_info),
            enabled = !isLoading,
            modifier = Modifier
                .placeholder(
                    visible = isLoading,
                    shape = CircleShape,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentAlbumActionsPreview() {
    AppTheme {
        CurrentAlbumActions(
            state = CurrentAlbumState(
                project = PreviewData.project,
            ),
            service = StreamingServices.QOBUZ,
            showMessage = {},
            showWebRoute = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentAlbumActionsLoadingPreview() {
    AppTheme {
        CurrentAlbumActions(
            state = CurrentAlbumState(
                project = PreviewData.project,
            ),
            service = StreamingServices.QOBUZ,
            showMessage = {},
            showWebRoute = {},
            isLoading = true,
        )
    }
}
