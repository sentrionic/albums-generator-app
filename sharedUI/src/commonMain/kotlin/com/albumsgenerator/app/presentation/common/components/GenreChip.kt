package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.utils.capitalize

@Composable
fun GenreChip(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    FilterChip(
        selected = true,
        onClick = onClick,
        label = {
            Text(text = label.capitalize())
        },
        modifier = modifier
            .clearAndSetSemantics {
                contentDescription = label
            },
        enabled = enabled,
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors().copy(
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSelectedContainerColor = MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.12f,
            ),
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun GenreChipPreview() {
    AppTheme {
        Column {
            GenreChip(label = "rock")
            GenreChip(
                label = "beatlesque",
                enabled = false,
            )
        }
    }
}
