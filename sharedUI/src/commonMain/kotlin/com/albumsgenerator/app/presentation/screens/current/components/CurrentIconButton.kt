package com.albumsgenerator.app.presentation.screens.current.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.albumsgenerator.app.presentation.common.components.Tooltip
import com.albumsgenerator.app.presentation.utils.collectIsScreenReaderEnabledAsState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentIconButton(
    iconRes: DrawableResource,
    onClick: () -> Unit,
    contentDescription: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val isScreenReaderOn by collectIsScreenReaderEnabledAsState()

    Tooltip(
        text = contentDescription,
        modifier = modifier,
        enableUserInput = !isScreenReaderOn,
    ) {
        FilledTonalIconButton(
            onClick = onClick,
            enabled = enabled,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription,
            )
        }
    }
}
