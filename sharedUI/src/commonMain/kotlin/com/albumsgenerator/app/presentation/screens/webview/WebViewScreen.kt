package com.albumsgenerator.app.presentation.screens.webview

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.navigate_up
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.albumsgenerator.app.presentation.common.components.Tooltip
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.Platform
import com.albumsgenerator.app.presentation.utils.collectIsScreenReaderEnabledAsState
import com.albumsgenerator.app.presentation.utils.getCurrentPlatform
import com.saralapps.composemultiplatformwebview.PlatformWebView
import com.saralapps.composemultiplatformwebview.rememberPlatformWebViewState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberPlatformWebViewState(url)
    val isScreenReaderOn by collectIsScreenReaderEnabledAsState()

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
                navigationIcon = {
                    Tooltip(
                        text = stringResource(Res.string.navigate_up),
                        enableUserInput = !isScreenReaderOn,
                    ) {
                        IconButton(
                            onClick = onBack,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.navigate_up),
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            if (getCurrentPlatform() == Platform.DESKTOP) {
                Text(
                    text = "You might need to resize the window for the content to show.",
                    modifier = Modifier
                        .padding(all = Paddings.large),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            PlatformWebView(
                state = state,
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
    }
}
