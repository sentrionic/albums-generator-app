package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings

@Composable
fun ErrorCard(
    message: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Paddings.large),
            verticalArrangement = Arrangement.spacedBy(Paddings.large),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.MusicOff,
                modifier = Modifier
                    .size(76.dp),
                contentDescription = null,
            )

            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun ErrorCardPreview() {
    AppTheme {
        ErrorCard(
            message = "Something went wrong. Try again later.",
        )
    }
}
