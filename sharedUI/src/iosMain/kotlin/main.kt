@file:Suppress("ktlint:standard:filename")

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeUIViewController
import com.albumsgenerator.app.App
import com.albumsgenerator.app.di.IOSAppGraph
import com.albumsgenerator.app.presentation.ui.theme.LightColorScheme
import dev.zacsweers.metro.createGraph
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.UIViewController
import platform.UIKit.setStatusBarStyle

@Suppress("ktlint:standard:function-naming", "FunctionName")
fun MainViewController(): UIViewController = ComposeUIViewController {
    val appGraph = createGraph<IOSAppGraph>()

    App(
        colorScheme = { LightColorScheme },
        metroVmf = appGraph.metroViewModelFactory,
    )
}

@Composable
private fun ThemeChanged(isDark: Boolean) {
    LaunchedEffect(isDark) {
        UIApplication.sharedApplication.setStatusBarStyle(
            if (isDark) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent,
        )
    }
}
