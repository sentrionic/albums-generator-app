package com.albumsgenerator.app.presentation.screens.current.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.presentation.common.components.NetworkImage
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.current.CurrentAlbumState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun CurrentAlbumContent(
    state: CurrentAlbumState,
    service: StreamingServices,
    showMessage: (String) -> Unit,
    navigateTo: (Route) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        state.project.currentAlbum.coverUrl?.let { image ->
            NetworkImage(
                url = image,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .aspectRatio(1f)
                    .clip(shape = MaterialTheme.shapes.large)
                    .placeholder(
                        visible = isLoading,
                        shape = MaterialTheme.shapes.large,
                    ),
            )
        }

        CurrentAlbumInfo(
            state = state,
            service = service,
            showMessage = showMessage,
            modifier = Modifier
                .padding(horizontal = Paddings.medium),
            navigateTo = navigateTo,
            isLoading = isLoading,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentAlbumContentPreview() {
    AppTheme {
        CurrentAlbumContent(
            state = CurrentAlbumState(
                project = PreviewData.project,
            ),
            service = StreamingServices.QOBUZ,
            showMessage = {},
            navigateTo = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentAlbumContentLoadingPreview() {
    AppTheme {
        CurrentAlbumContent(
            state = CurrentAlbumState(
                project = PreviewData.project,
            ),
            service = StreamingServices.QOBUZ,
            showMessage = {},
            navigateTo = {},
            isLoading = true,
        )
    }
}
