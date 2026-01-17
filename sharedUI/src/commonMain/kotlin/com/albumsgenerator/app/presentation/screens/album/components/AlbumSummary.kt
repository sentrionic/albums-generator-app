package com.albumsgenerator.app.presentation.screens.album.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_summary
import albumsgenerator.sharedui.generated.resources.album_summary_read_more
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.components.SectionHeader
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.PreviewData
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlbumSummary(
    summary: String,
    openSummaryPage: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(all = Paddings.medium),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        ) {
            SectionHeader(text = stringResource(Res.string.album_summary))

            Text(
                text = summary,
                modifier = Modifier
                    .placeholder(visible = isLoading),
            )

            TextButton(
                onClick = openSummaryPage,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(Res.string.album_summary_read_more),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlbumSummaryPreview() {
    AppTheme {
        AlbumSummary(
            summary = PreviewData.album.summary.orEmpty(),
            openSummaryPage = {},
        )
    }
}
