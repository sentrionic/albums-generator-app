package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.close
import albumsgenerator.sharedui.generated.resources.ic_arrow_drop_down
import albumsgenerator.sharedui.generated.resources.ic_check
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Suppress("EffectKeys")
@Composable
fun <T : Any> DropdownMenu(
    label: String,
    items: ImmutableList<T>,
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

    Box(modifier = modifier) {
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(showMenu) {
            if (showMenu) {
                delay(500.milliseconds)
                focusRequester.requestFocus()
            }
        }
        DropdownMenuButton(
            selected = current != null,
            label = buttonText,
            openMenu = { showMenu = true },
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    role = Role.DropdownList
                },
        )

        DropdownMenuContent(
            showMenu = showMenu,
            hideMenu = { showMenu = false },
            label = label,
            items = items,
            current = current,
            onSelect = onSelect,
            onReset = onReset,
            formatItem = formatItem,
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
private fun DropdownMenuButton(
    selected: Boolean,
    label: String,
    openMenu: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
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
        leadingIcon = if (selected) {
            {
                Icon(
                    painter = painterResource(Res.drawable.ic_check),
                    contentDescription = null,
                )
            }
        } else {
            null
        },
        trailingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_drop_down),
                contentDescription = null,
            )
        },
        shape = CircleShape,
    )
}

@Composable
private fun <T : Any> DropdownMenuContent(
    showMenu: Boolean,
    hideMenu: () -> Unit,
    label: String,
    items: ImmutableList<T>,
    current: T?,
    onSelect: (T) -> Unit,
    onReset: () -> Unit,
    formatItem: (T) -> String,
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
        DropdownItem(
            label = label,
            onSelect = {
                onReset()
                hideMenu()
            },
            isItemCurrent = false,
            leadingIcon = null,
        )

        for (item in items) {
            DropdownItem(
                label = formatItem(item),
                onSelect = {
                    onSelect(item)
                    hideMenu()
                },
                isItemCurrent = item == current,
                leadingIcon = null,
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
