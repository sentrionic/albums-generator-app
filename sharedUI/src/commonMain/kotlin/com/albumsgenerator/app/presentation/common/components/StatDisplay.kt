package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun StatDisplay(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier
            .semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            modifier = Modifier
                .placeholder(visible = isLoading),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = label,
            modifier = Modifier
                .placeholder(visible = isLoading),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatDisplayPreview() {
    AppTheme {
        StatDisplay(
            label = "RATING",
            value = "3.14",
        )
    }
}
