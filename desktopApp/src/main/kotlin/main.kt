import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.albumsgenerator.app.App
import com.albumsgenerator.app.di.JvmAppGraph
import com.albumsgenerator.app.domain.core.Theme
import com.albumsgenerator.app.presentation.screens.splash.logoPainter
import com.albumsgenerator.app.presentation.ui.theme.DarkColorScheme
import com.albumsgenerator.app.presentation.ui.theme.LightColorScheme
import dev.zacsweers.metro.createGraph
import java.awt.Dimension

fun main() = application {
    val appGraph = createGraph<JvmAppGraph>()
    val icon = logoPainter

    Tray(
        icon = icon,
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 500.dp, height = 1000.dp),
        title = "Albums Generator",
        icon = icon,
    ) {
        window.minimumSize = Dimension(350, 600)
        App(
            metroVmf = appGraph.metroViewModelFactory,
            colorScheme = {
                val isDark = when (it) {
                    Theme.LIGHT -> false
                    Theme.DARK -> true
                    else -> isSystemInDarkTheme()
                }

                if (isDark) {
                    DarkColorScheme
                } else {
                    LightColorScheme
                }
            },
        )
    }
}

