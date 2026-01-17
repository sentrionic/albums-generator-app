package com.albumsgenerator.app.presentation.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.presentation.screens.login.components.LoginContent
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel

@Composable
fun LoginScreen(
    onContinue: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = assistedMetroViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state, onContinue) {
        state.nextRoute?.let { onContinue(it) }
    }

    LoginContent(
        name = viewModel.name,
        state = state,
        onUpdateName = viewModel::update,
        onAuthenticate = viewModel::fetchProject,
        modifier = modifier,
    )
}
