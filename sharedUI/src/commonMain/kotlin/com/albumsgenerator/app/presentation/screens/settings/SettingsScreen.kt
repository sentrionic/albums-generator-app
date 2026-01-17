package com.albumsgenerator.app.presentation.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.presentation.screens.settings.components.SettingsContent
import dev.zacsweers.metrox.viewmodel.metroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateTo: (NavKey) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = metroViewModel(),
) {
    val project by viewModel.project.collectAsStateWithLifecycle()
    val userData by viewModel.userData.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.shouldLogout, onLogout) {
        if (viewModel.shouldLogout) {
            onLogout()
        }
    }

    SettingsContent(
        project = project,
        userData = userData,
        message = viewModel.message,
        navigateTo = navigateTo,
        sendEvent = viewModel::onEvent,
        modifier = modifier,
    )
}
