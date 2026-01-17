package com.albumsgenerator.app.androidApp

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import com.albumsgenerator.app.App
import com.albumsgenerator.app.di.AndroidAppGraph
import com.albumsgenerator.app.domain.core.Theme
import com.albumsgenerator.app.presentation.ui.theme.DarkColorScheme
import com.albumsgenerator.app.presentation.ui.theme.LightColorScheme
import dev.zacsweers.metro.createGraphFactory

class MainActivity() : ComponentActivity() {
    // TODO: Replace with BaseApplication application inject
    val appGraph = createGraphFactory<AndroidAppGraph.Factory>().create(activity = this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           App(
               metroVmf = appGraph.metroViewModelFactory,
                colorScheme = {
                    val isDark = when (it) {
                        Theme.LIGHT -> false
                        Theme.DARK -> true
                        else -> isSystemInDarkTheme()
                    }

                    ThemeChanged(isDark)

                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                            val context = LocalContext.current
                            if (isDark) {
                                dynamicDarkColorScheme(context)
                            } else {
                                dynamicLightColorScheme(context)
                            }
                        }

                        isDark -> {
                            DarkColorScheme
                        }

                        else -> {
                            LightColorScheme
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun ThemeChanged(isDark: Boolean) {
    val view = LocalView.current
    LaunchedEffect(isDark) {
        val window = (view.context as Activity).window
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = !isDark
            isAppearanceLightNavigationBars = !isDark
        }
    }
}
