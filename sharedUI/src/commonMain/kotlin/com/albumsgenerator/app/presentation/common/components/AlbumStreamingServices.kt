package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import org.jetbrains.compose.resources.painterResource

@Composable
fun AlbumStreamingServices(
    streamingServices: List<StreamingServices>,
    serviceUrl: (StreamingServices) -> String,
    openUri: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val maxItemsInEachRow = remember(streamingServices) {
        val size = streamingServices.size
        if (size > 4) {
            (size + 1) / 2
        } else {
            size
        }
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = Paddings.small,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalArrangement = Arrangement.Center,
        itemVerticalAlignment = Alignment.CenterVertically,
        maxItemsInEachRow = maxItemsInEachRow,
    ) {
        for (service in streamingServices) {
            ServiceButton(
                id = serviceUrl(service),
                service = service,
                onClick = openUri,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServiceButton(
    id: String,
    service: StreamingServices,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            TooltipAnchorPosition.Above,
        ),
        tooltip = {
            PlainTooltip {
                Text(service.label)
            }
        },
        state = rememberTooltipState(),
        modifier = modifier,
    ) {
        FilledTonalIconButton(
            onClick = {
                onClick("${service.url}$id")
            },
        ) {
            Icon(
                painter = painterResource(service.logo),
                contentDescription = service.label,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumStreamingServicesPreview() {
    AppTheme {
        AlbumStreamingServices(
            streamingServices = PreviewData.album.streamingServices,
            serviceUrl = { PreviewData.album.serviceUrl(it) },
            openUri = {},
        )
    }
}
