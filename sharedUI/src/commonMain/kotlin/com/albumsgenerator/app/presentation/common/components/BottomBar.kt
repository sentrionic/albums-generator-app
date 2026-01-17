package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.destination_album
import albumsgenerator.sharedui.generated.resources.destination_history
import albumsgenerator.sharedui.generated.resources.destination_settings
import albumsgenerator.sharedui.generated.resources.destination_stats
import albumsgenerator.sharedui.generated.resources.destination_summary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class NavigationItem(val label: StringResource, val icon: ImageVector)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.CurrentAlbum to NavigationItem(
        label = Res.string.destination_album,
        icon = Icons.Default.Album,
    ),
    Route.History to NavigationItem(
        label = Res.string.destination_history,
        icon = Icons.Filled.History,
    ),
    Route.Stats to NavigationItem(
        label = Res.string.destination_stats,
        icon = Icons.Filled.AutoGraph,
    ),
    Route.Summary to NavigationItem(
        label = Res.string.destination_summary,
        icon = Icons.Default.Summarize,
    ),
    Route.Settings to NavigationItem(
        label = Res.string.destination_settings,
        icon = Icons.Default.Settings,
    ),
)

@Composable
fun BottomBar(
    current: NavKey,
    onSelect: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        for ((destination, data) in TOP_LEVEL_DESTINATIONS) {
            NavigationBarItem(
                selected = current == destination,
                onClick = {
                    onSelect(destination as NavKey)
                },
                icon = {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(data.label),
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    AppTheme {
        BottomBar(
            current = Route.CurrentAlbum,
            onSelect = {},
        )
    }
}
