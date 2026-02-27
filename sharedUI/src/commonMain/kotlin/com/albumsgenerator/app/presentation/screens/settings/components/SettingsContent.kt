package com.albumsgenerator.app.presentation.screens.settings.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.action_error
import albumsgenerator.sharedui.generated.resources.copied_to_clipboard
import albumsgenerator.sharedui.generated.resources.destination_settings
import albumsgenerator.sharedui.generated.resources.logout
import albumsgenerator.sharedui.generated.resources.settings_app_info_action
import albumsgenerator.sharedui.generated.resources.settings_app_info_body
import albumsgenerator.sharedui.generated.resources.settings_app_info_title
import albumsgenerator.sharedui.generated.resources.settings_external_info_action
import albumsgenerator.sharedui.generated.resources.settings_external_info_body
import albumsgenerator.sharedui.generated.resources.settings_external_info_title
import albumsgenerator.sharedui.generated.resources.settings_refresh_action
import albumsgenerator.sharedui.generated.resources.settings_refresh_body
import albumsgenerator.sharedui.generated.resources.settings_refresh_title
import albumsgenerator.sharedui.generated.resources.settings_sharing_action
import albumsgenerator.sharedui.generated.resources.settings_sharing_body
import albumsgenerator.sharedui.generated.resources.settings_sharing_title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import co.touchlab.kermit.Logger
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.domain.models.Project
import com.albumsgenerator.app.domain.models.UserData
import com.albumsgenerator.app.presentation.common.components.BottomBar
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.settings.SettingsEvents
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.Platform
import com.albumsgenerator.app.presentation.utils.clipEntryOf
import com.albumsgenerator.app.presentation.utils.getCurrentPlatform
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    project: Project?,
    userData: UserData?,
    message: StringResource?,
    navigateTo: (NavKey) -> Unit,
    sendEvent: (SettingsEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val clipboard = LocalClipboard.current

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val title = stringResource(Res.string.destination_settings)

    LaunchedEffect(message, sendEvent) {
        val snackbar = message?.let { getString(it) }
        if (!snackbar.isNullOrEmpty()) {
            snackbarHostState.showSnackbar(snackbar)
            sendEvent(SettingsEvents.ResetMessage)
        }
    }

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
                current = Route.Settings,
                onSelect = navigateTo,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(all = Paddings.large),
            verticalArrangement = Arrangement.spacedBy(Paddings.large),
        ) {
            SettingsCardWithAction(
                label = stringResource(Res.string.settings_sharing_title),
                icon = Icons.Filled.Share,
                subtitle = stringResource(Res.string.settings_sharing_body),
                actionLabel = stringResource(Res.string.settings_sharing_action),
                onAction = {
                    scope.launch {
                        clipboard.setClipEntry(clipEntryOf(project?.shareableUrl.orEmpty()))

                        if (getCurrentPlatform() != Platform.ANDROID) {
                            snackbarHostState.showSnackbar(
                                getString(Res.string.copied_to_clipboard),
                            )
                        }
                    }
                },
            )

            if (userData != null) {
                SettingsOptions(
                    userData = userData,
                    sendEvent = sendEvent,
                )

                SpoilerOptionSection(
                    spoilerMode = userData.spoilerMode,
                    onSpoilerModeChange = {
                        sendEvent(SettingsEvents.UpdateSpoilerMode(it))
                    },
                )
            }

            SettingsCardWithAction(
                label = stringResource(Res.string.settings_refresh_title),
                icon = Icons.Filled.Sync,
                subtitle = stringResource(Res.string.settings_refresh_body),
                actionLabel = stringResource(Res.string.settings_refresh_action),
                onAction = {
                    sendEvent(SettingsEvents.Refresh)
                },
            )

            SettingsCardWithAction(
                label = stringResource(Res.string.settings_external_info_title),
                icon = Icons.Filled.Web,
                subtitle = stringResource(Res.string.settings_external_info_body),
                actionLabel = stringResource(Res.string.settings_external_info_action),
                onAction = {
                    navigateTo(
                        Route.Web(
                            url = "${Constants.WEBSITE_URL}/${project?.name}/info",
                            title = "Info",
                        ),
                    )
                },
            )

            SettingsCardWithAction(
                label = stringResource(Res.string.settings_app_info_title),
                icon = Icons.Filled.DeveloperMode,
                subtitle = stringResource(Res.string.settings_app_info_body),
                actionLabel = stringResource(Res.string.settings_app_info_action),
                onAction = {
                    try {
                        uriHandler.openUri(Constants.APP_REPOSITORY)
                    } catch (e: Exception) {
                        Logger.e(e) { "Could not open the url" }
                        scope.launch {
                            snackbarHostState.showSnackbar(getString(Res.string.action_error))
                        }
                    }
                },
            )

            OutlinedButton(
                onClick = {
                    sendEvent(SettingsEvents.Logout)
                },
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(Res.string.logout),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsContentPreview() {
    AppTheme {
        SettingsContent(
            project = null,
            userData = UserData.EMPTY,
            message = null,
            navigateTo = {},
            sendEvent = {},
        )
    }
}
