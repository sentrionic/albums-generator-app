package com.albumsgenerator.app.presentation.screens.current.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.current.CurrentAlbumState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.albumsgenerator.app.presentation.utils.capitalize
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun CurrentAlbumInfo(
    state: CurrentAlbumState,
    service: StreamingServices,
    showMessage: (String) -> Unit,
    navigateTo: (Route) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val album = remember(state) { state.project.currentAlbum }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = album.name,
            modifier = Modifier
                .placeholder(visible = isLoading),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(vertical = Paddings.small)
                .placeholder(visible = isLoading),
            thickness = 3.dp,
        )

        Text(
            text = album.artist,
            modifier = Modifier
                .placeholder(visible = isLoading),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = "${album.genres.firstOrNull()?.capitalize()} • ${album.releaseDate}",
            modifier = Modifier
                .placeholder(visible = isLoading),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )

        CurrentAlbumActions(
            state = state,
            service = service,
            showMessage = showMessage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Paddings.large),
            showWebRoute = navigateTo,
            isLoading = isLoading,
        )

        Spacer(modifier = Modifier.height(Paddings.medium))
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentAlbumInfoPreview() {
    AppTheme {
        CurrentAlbumInfo(
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
private fun CurrentAlbumInfoLoadingPreview() {
    AppTheme {
        CurrentAlbumInfo(
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
