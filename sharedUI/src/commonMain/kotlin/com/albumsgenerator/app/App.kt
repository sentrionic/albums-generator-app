package com.albumsgenerator.app

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.albumsgenerator.app.domain.core.Theme
import com.albumsgenerator.app.presentation.navigation.NavGraph
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.metroViewModel

@Composable
@Suppress(
    "ktlint:compose:modifier-missing-check",
    "ktlint:compose:vm-injection-check",
    "ModifierRequired",
)
fun App(
    metroVmf: MetroViewModelFactory,
    colorScheme: @Composable (Theme?) -> ColorScheme,
) {
    CompositionLocalProvider(LocalMetroViewModelFactory provides metroVmf) {
        val viewModel = metroViewModel<MainViewModel>()
        val userData by viewModel.userData.collectAsStateWithLifecycle()
        AppTheme(
            colorScheme = colorScheme(userData?.theme),
        ) {
            NavGraph(
                backStack = viewModel.backStack,
                navigateTo = {
                    if (it in viewModel.backStack) {
                        viewModel.backStack.remove(it)
                    }
                    viewModel.backStack.add(it)
                },
                onBack = {
                    if (viewModel.backStack.isNotEmpty()) {
                        viewModel.backStack.removeLastOrNull()
                    }
                },
                replaceWith = viewModel::replace,
            )
        }
    }
}
