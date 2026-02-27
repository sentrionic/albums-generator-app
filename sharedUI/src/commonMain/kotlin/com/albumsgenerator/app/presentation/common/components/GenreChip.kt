package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.navigate_accessibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.utils.capitalize
import org.jetbrains.compose.resources.stringResource

@Composable
fun GenreChip(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    val navigateLabel = stringResource(Res.string.navigate_accessibility)

    FilterChip(
        selected = true,
        onClick = onClick,
        label = {
            Text(text = label.capitalize())
        },
        modifier = modifier
            .clearAndSetSemantics {
                stateDescription = label
                if (enabled) {
                    role = Role.Button
                    onClick(
                        label = navigateLabel,
                        action = {
                            onClick()
                            true
                        },
                    )
                }
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
