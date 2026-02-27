package com.albumsgenerator.app.presentation.screens.settings.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.destination_settings
import albumsgenerator.sharedui.generated.resources.please_choose
import albumsgenerator.sharedui.generated.resources.settings_streaming_source
import albumsgenerator.sharedui.generated.resources.settings_theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.core.Theme
import com.albumsgenerator.app.domain.models.UserData
import com.albumsgenerator.app.presentation.common.components.SelectionMenu
import com.albumsgenerator.app.presentation.screens.settings.SettingsEvents
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsOptions(
    userData: UserData,
    sendEvent: (SettingsEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsCard(
        label = stringResource(Res.string.destination_settings),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
        ) {
            Text(
                text = stringResource(Res.string.settings_streaming_source),
                style = MaterialTheme.typography.titleSmall,
            )

            SelectionMenu(
                label = userData.service?.label ?: stringResource(Res.string.please_choose),
                items = StreamingServices.entries,
                onSelect = {
                    sendEvent(SettingsEvents.UpdateStreamingService(it))
                },
                formatItem = { it.label },
                leadingIcon = {
                    {
                        Icon(
                            painter = painterResource(it.logo),
                            contentDescription = null,
                        )
                    }
                },
                isItemCurrent = { it == userData.service },
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
        ) {
            Text(
                text = stringResource(Res.string.settings_theme),
                style = MaterialTheme.typography.titleSmall,
            )

            SelectionMenu(
                label = stringResource(userData.theme.label),
                items = Theme.entries,
                onSelect = {
                    sendEvent(SettingsEvents.UpdateTheme(it))
                },
                formatItem = { stringResource(it.label) },
                leadingIcon = {
                    {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = null,
                        )
                    }
                },
                isItemCurrent = { it == userData.theme },
            )
        }
    }
}

@Preview
@Composable
private fun SettingsOptionsPreview() {
    AppTheme {
        SettingsOptions(
            userData = UserData.EMPTY,
            sendEvent = {},
        )
    }
}
