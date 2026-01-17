package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings

@Composable
fun Section(
    header: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SectionHeader(text = header)

        content()
    }
}

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .semantics { heading() },
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Preview(showBackground = true)
@Composable
private fun SectionPreview() {
    AppTheme {
        Section(header = "Header") {
            Text("Content")
        }
    }
}
