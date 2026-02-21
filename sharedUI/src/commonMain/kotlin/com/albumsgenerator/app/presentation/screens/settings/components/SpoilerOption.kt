package com.albumsgenerator.app.presentation.screens.settings.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.spoiler_mode_label
import albumsgenerator.sharedui.generated.resources.spoiler_mode_title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.screens.settings.SettingsEvents
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.stringResource

@Composable
fun SpoilerOption(
    spoilerFree: Boolean,
    onSpoilerFreeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
    ) {
        Text(
            text = stringResource(Res.string.spoiler_mode_title),
            style = MaterialTheme.typography.titleSmall,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .minimumInteractiveComponentSize()
                .toggleable(
                    value = spoilerFree,
                    role = Role.Switch,
                    onValueChange = onSpoilerFreeChange,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.spoiler_mode_label),
                modifier = Modifier
                    .weight(1f),
            )

            Switch(
                checked = spoilerFree,
                onCheckedChange = null,
                thumbContent = if (spoilerFree) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

@Preview
@Composable
private fun SpoilerOptionPreview() {
    AppTheme {
        SpoilerOption(
            spoilerFree = true,
            onSpoilerFreeChange = {},
        )
    }
}
