package com.albumsgenerator.app.presentation.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.components.SectionHeader
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings

@Composable
fun SettingsCard(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Paddings.large),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        ) {
            SectionHeader(text = label)

            content()
        }
    }
}

@Composable
fun SettingsCardWithAction(
    label: String,
    icon: ImageVector,
    subtitle: String,
    actionLabel: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsCard(
        label = label,
        modifier = modifier,
    ) {
        Text(subtitle)

        TextButton(
            onClick = onAction,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Paddings.small),
            ) {
                Text(
                    text = actionLabel,
                    style = MaterialTheme.typography.titleMedium,
                )

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsCardWithActionPreview() {
    AppTheme {
        SettingsCardWithAction(
            label = "Section Header",
            icon = Icons.Filled.MusicNote,
            subtitle = "This is a section",
            actionLabel = "Click Me",
            onAction = {},
        )
    }
}
