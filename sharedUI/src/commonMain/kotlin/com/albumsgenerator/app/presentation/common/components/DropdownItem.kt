package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.dropdown_item_selected
import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import org.jetbrains.compose.resources.stringResource

@Composable
fun DropdownItem(
    label: String,
    onSelect: () -> Unit,
    isItemCurrent: Boolean,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val stateLabel = if (isItemCurrent) {
        stringResource(Res.string.dropdown_item_selected)
    } else {
        ""
    }

    DropdownMenuItem(
        text = {
            Text(
                text = label,
            )
        },
        onClick = onSelect,
        modifier = modifier
            .background(
                if (isItemCurrent) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                },
            )
            .semantics {
                stateDescription = stateLabel
            },
        leadingIcon = if (leadingIcon != null) {
            { leadingIcon() }
        } else {
            null
        },
        colors = MenuDefaults.itemColors().copy(
            textColor = if (isItemCurrent) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            leadingIconColor = if (isItemCurrent) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
        ),
    )
}
