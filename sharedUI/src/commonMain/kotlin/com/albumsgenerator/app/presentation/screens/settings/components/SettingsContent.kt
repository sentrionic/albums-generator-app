package com.albumsgenerator.app.presentation.screens.settings.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.destination_settings
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.domain.models.Project
import com.albumsgenerator.app.domain.models.UserData
import com.albumsgenerator.app.presentation.common.components.BottomBar
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.settings.SettingsEvents
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
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
        SettingsLayout(
            project = project,
            userData = userData,
            navigateTo = navigateTo,
            sendEvent = sendEvent,
            showMessage = {
                snackbarHostState.showSnackbar(it)
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(all = Paddings.large),
        )
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
