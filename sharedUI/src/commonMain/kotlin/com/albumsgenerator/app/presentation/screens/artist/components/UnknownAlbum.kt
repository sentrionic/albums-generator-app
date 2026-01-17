package com.albumsgenerator.app.presentation.screens.artist.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.unknown_album
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.stringResource

@Composable
fun UnknownAlbum(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            ) {
                Icon(
                    imageVector = Icons.Filled.QuestionMark,
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .align(Alignment.Center),
                )
            }

            Column(
                modifier = Modifier
                    .padding(all = Paddings.large),
                verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
            ) {
                Text(
                    text = stringResource(Res.string.unknown_album),
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    minLines = 2,
                    maxLines = 2,
                )

                Text(
                    text = "?",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Text(
                    text = "? / 5",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview
@Composable
private fun UnknownAlbumPreview() {
    AppTheme {
        UnknownAlbum()
    }
}
