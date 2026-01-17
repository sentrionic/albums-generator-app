package com.albumsgenerator.app.presentation.screens.rate

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.previous_album_action
import albumsgenerator.sharedui.generated.resources.previous_album_title
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
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
import com.albumsgenerator.app.BuildConfig
import com.albumsgenerator.app.presentation.common.components.Tooltip
import com.albumsgenerator.app.presentation.utils.collectIsScreenReaderEnabledAsState
import com.saralapps.composemultiplatformwebview.PlatformWebView
import com.saralapps.composemultiplatformwebview.rememberPlatformWebViewState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateAlbumScreen(
    projectName: String,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberPlatformWebViewState(
        url = "${BuildConfig.WEBSITE_URL}/$projectName",
        allowsFileAccess = false,
    )
    val isScreenReaderOn by collectIsScreenReaderEnabledAsState()
    val title = stringResource(Res.string.previous_album_title)

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
                actions = {
                    val description = stringResource(Res.string.previous_album_action)
                    Tooltip(
                        text = description,
                        enableUserInput = !isScreenReaderOn,
                    ) {
                        FilledIconButton(
                            onClick = onContinue,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                contentDescription = description,
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        PlatformWebView(
            state = state,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        )
    }
}
