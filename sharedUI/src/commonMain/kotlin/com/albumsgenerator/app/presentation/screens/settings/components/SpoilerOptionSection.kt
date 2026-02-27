package com.albumsgenerator.app.presentation.screens.settings.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.spoiler_mode_title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.models.SpoilerMode
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.stringResource

@Composable
fun SpoilerOptionSection(
    spoilerMode: SpoilerMode,
    onSpoilerModeChange: (SpoilerMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsCard(
        label = stringResource(Res.string.spoiler_mode_title),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(Paddings.small),
        ) {
            for (mode in SpoilerMode.entries) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .selectable(
                            selected = spoilerMode == mode,
                            role = Role.RadioButton,
                            onClick = {
                                onSpoilerModeChange(mode)
                            },
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Text(
                            text = stringResource(mode.label),
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Text(
                            text = stringResource(mode.description),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    RadioButton(
                        selected = mode == spoilerMode,
                        onClick = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SpoilerOptionSectionPreview() {
    AppTheme {
        SpoilerOptionSection(
            spoilerMode = SpoilerMode.VISIBLE,
            onSpoilerModeChange = {},
            modifier = Modifier
                .padding(all = Paddings.medium),
        )
    }
}
