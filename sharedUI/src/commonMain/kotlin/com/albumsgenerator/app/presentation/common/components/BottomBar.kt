package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.destination_album
import albumsgenerator.sharedui.generated.resources.destination_history
import albumsgenerator.sharedui.generated.resources.destination_settings
import albumsgenerator.sharedui.generated.resources.destination_stats
import albumsgenerator.sharedui.generated.resources.destination_summary
import albumsgenerator.sharedui.generated.resources.ic_album
import albumsgenerator.sharedui.generated.resources.ic_history
import albumsgenerator.sharedui.generated.resources.ic_query_stats
import albumsgenerator.sharedui.generated.resources.ic_settings
import albumsgenerator.sharedui.generated.resources.ic_summarize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
                        painter = painterResource(data.icon),
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

data class NavigationItem(val label: StringResource, val icon: DrawableResource)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.CurrentAlbum to NavigationItem(
        label = Res.string.destination_album,
        icon = Res.drawable.ic_album,
    ),
    Route.History to NavigationItem(
        label = Res.string.destination_history,
        icon = Res.drawable.ic_history,
    ),
    Route.Stats to NavigationItem(
        label = Res.string.destination_stats,
        icon = Res.drawable.ic_query_stats,
    ),
    Route.Summary to NavigationItem(
        label = Res.string.destination_summary,
        icon = Res.drawable.ic_summarize,
    ),
    Route.Settings to NavigationItem(
        label = Res.string.destination_settings,
        icon = Res.drawable.ic_settings,
    ),
)

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
