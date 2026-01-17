package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.action_open_web
import albumsgenerator.sharedui.generated.resources.ic_external_link
import albumsgenerator.sharedui.generated.resources.navigate_up
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.albumsgenerator.app.presentation.utils.collectIsScreenReaderEnabledAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onBack: () -> Unit,
    onOpenWeb: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isScreenReaderOn by collectIsScreenReaderEnabledAsState()

    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier,
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
        actions = {
            Tooltip(
                text = stringResource(Res.string.action_open_web),
                enableUserInput = !isScreenReaderOn,
            ) {
                IconButton(
                    onClick = onOpenWeb,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_external_link),
                        contentDescription = stringResource(Res.string.action_open_web),
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun AppBarPreview() {
    AppTheme {
        AppBar(
            title = PreviewData.album.name,
            onBack = {},
            onOpenWeb = {},
        )
    }
}
