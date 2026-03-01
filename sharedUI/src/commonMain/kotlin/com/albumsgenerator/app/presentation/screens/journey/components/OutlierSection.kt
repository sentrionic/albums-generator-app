package com.albumsgenerator.app.presentation.screens.journey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.modifiers.listSemantics
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.eygraber.compose.placeholder.material3.placeholder

@Composable
fun OutlierSection(
    title: String,
    albumsCount: Int,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(all = Paddings.medium)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.large),
        ) {
            Text(
                text = title.uppercase(),
                modifier = Modifier
                    .semantics { heading() }
                    .placeholder(isLoading),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium,
            )

            Column(
                modifier = Modifier
                    .listSemantics(albumsCount),
                verticalArrangement = Arrangement.spacedBy(Paddings.small),
                content = content,
            )
        }
    }
}

@Preview
@Composable
private fun OutlierSectionPreview() {
    AppTheme {
        OutlierSection(
            title = "Outlier",
            albumsCount = 2,
        ) {
        }
    }
}
