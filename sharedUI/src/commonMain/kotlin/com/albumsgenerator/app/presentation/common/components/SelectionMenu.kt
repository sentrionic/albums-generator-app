package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.close
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.albumsgenerator.app.presentation.common.modifiers.onEscape
import com.albumsgenerator.app.presentation.ui.theme.AspectRatios
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Suppress("EffectKeys")
@Composable
fun <T : Any> SelectionMenu(
    label: String,
    items: ImmutableList<T>,
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

    Box(modifier = modifier) {
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(showMenu) {
            if (showMenu) {
                delay(500.milliseconds)
                focusRequester.requestFocus()
            }
        }

        SelectionMenuButton(
            selected = current != null,
            label = buttonText,
            openMenu = { showMenu = true },
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    role = Role.DropdownList
                },
            leadingIcon = if (current != null) {
                leadingIcon(current)
            } else {
                null
            },
        )

        SelectionMenuContent(
            showMenu = showMenu,
            hideMenu = { showMenu = false },
            items = items,
            current = current,
            onSelect = onSelect,
            formatItem = formatItem,
            leadingIcon = leadingIcon,
            modifier = Modifier
                .fillMaxWidth(AspectRatios.ONE_TO_EIGHT)
                .aspectRatio(AspectRatios.ONE_TO_EIGHT)
                .focusRequester(focusRequester)
                .onEscape {
                    showMenu = false
                },
        )
    }
}

@Composable
private fun SelectionMenuButton(
    selected: Boolean,
    label: String,
    openMenu: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    FilterChip(
        selected = selected,
        onClick = openMenu,
        label = {
            Text(
                text = label,
                modifier = Modifier
                    .padding(all = Paddings.small),
            )
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
            )
        },
        shape = CircleShape,
        border = AssistChipDefaults.assistChipBorder(enabled),
    )
}

@Composable
private fun <T : Any> SelectionMenuContent(
    showMenu: Boolean,
    hideMenu: () -> Unit,
    items: ImmutableList<T>,
    current: T?,
    onSelect: (T) -> Unit,
    formatItem: @Composable (T) -> String,
    leadingIcon: (T) -> @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        val index = items.indexOfFirst { it == current }
        if (index != -1) {
            scrollState.scrollTo(index)
        }
    }

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = hideMenu,
        modifier = modifier,
        scrollState = scrollState,
    ) {
        for (item in items) {
            DropdownItem(
                label = formatItem(item),
                onSelect = {
                    onSelect(item)
                    hideMenu()
                },
                isItemCurrent = item == current,
                leadingIcon = leadingIcon(item),
            )
        }

        HorizontalDivider()

        DropdownItem(
            label = stringResource(Res.string.close),
            onSelect = hideMenu,
            isItemCurrent = false,
            leadingIcon = null,
        )
    }
}
