package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.close
import albumsgenerator.sharedui.generated.resources.dropdown_item_selected
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import com.albumsgenerator.app.presentation.common.modifiers.onEscape
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Suppress("EffectKeys")
@Composable
fun <T : Any> DropdownMenu(
    label: String,
    items: List<T>,
    onSelect: (T) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    formatItem: (T) -> String = { it.toString() },
    isItemCurrent: (T) -> Boolean = { false },
    enabled: Boolean = true,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    val current = items.firstOrNull { isItemCurrent(it) }

    val buttonText = remember(current, label) {
        if (current != null) {
            formatItem(current)
        } else {
            label
        }
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        val index = items.indexOfFirst { it == current }
        if (index != -1) {
            scrollState.scrollTo(index)
        }
    }

    Box(modifier = modifier) {
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(showMenu) {
            if (showMenu) {
                delay(500)
                focusRequester.requestFocus()
            }
        }

        FilterChip(
            selected = current != null,
            onClick = {
                showMenu = true
            },
            label = {
                Text(
                    text = buttonText,
                    modifier = Modifier
                        .padding(all = Paddings.small),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    role = Role.DropdownList
                },
            enabled = enabled,
            leadingIcon = if (current != null) {
                {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                    )
                }
            } else {
                null
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                )
            },
            shape = CircleShape,
        )

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(0.8f)
                .focusRequester(focusRequester)
                .onEscape {
                    showMenu = false
                },
            scrollState = scrollState,
        ) {
            DropdownItem(
                label = label,
                onSelect = {
                    onReset()
                    showMenu = false
                },
                isItemCurrent = false,
                leadingIcon = null,
            )

            for (item in items) {
                DropdownItem(
                    label = formatItem(item),
                    onSelect = {
                        onSelect(item)
                        showMenu = false
                    },
                    isItemCurrent = item == current,
                    leadingIcon = null,
                )
            }

            HorizontalDivider()

            DropdownItem(
                label = stringResource(Res.string.close),
                onSelect = {
                    showMenu = false
                },
                isItemCurrent = false,
                leadingIcon = null,
            )
        }
    }
}

@Suppress("EffectKeys")
@Composable
fun <T : Any> SelectionMenu(
    label: String,
    items: List<T>,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    formatItem: @Composable (T) -> String = { it.toString() },
    leadingIcon: (T) -> @Composable (() -> Unit)? = { null },
    isItemCurrent: (T) -> Boolean = { false },
    enabled: Boolean = true,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    val current = items.firstOrNull { isItemCurrent(it) }

    val currentLabelOrNull = current?.let { formatItem(it) }
    val buttonText = remember(currentLabelOrNull, label) {
        currentLabelOrNull ?: label
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        val index = items.indexOfFirst { it == current }
        if (index != -1) {
            scrollState.scrollTo(index)
        }
    }

    Box(modifier = modifier) {
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(showMenu) {
            if (showMenu) {
                delay(500)
                focusRequester.requestFocus()
            }
        }

        FilterChip(
            selected = current != null,
            onClick = {
                showMenu = true
            },
            label = {
                Text(
                    text = buttonText,
                    modifier = Modifier
                        .padding(all = Paddings.small),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    role = Role.DropdownList
                },
            enabled = enabled,
            leadingIcon = if (current != null) {
                leadingIcon(current)
            } else {
                null
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                )
            },
            shape = CircleShape,
            border = AssistChipDefaults.assistChipBorder(enabled),
        )

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(0.8f)
                .focusRequester(focusRequester)
                .onEscape {
                    showMenu = false
                },
            scrollState = scrollState,
        ) {
            for (item in items) {
                DropdownItem(
                    label = formatItem(item),
                    onSelect = {
                        onSelect(item)
                        showMenu = false
                    },
                    isItemCurrent = item == current,
                    leadingIcon = leadingIcon(item),
                )
            }

            HorizontalDivider()

            DropdownItem(
                label = stringResource(Res.string.close),
                onSelect = {
                    showMenu = false
                },
                isItemCurrent = false,
                leadingIcon = null,
            )
        }
    }
}

@Composable
private fun DropdownItem(
    label: String,
    onSelect: () -> Unit,
    isItemCurrent: Boolean,
    leadingIcon: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
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
