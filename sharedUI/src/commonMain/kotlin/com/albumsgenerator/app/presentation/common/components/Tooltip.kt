package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tooltip(
    text: String,
    modifier: Modifier = Modifier,
    focusable: Boolean = true,
    enableUserInput: Boolean = true,
    showTooltipByShortPress: Boolean = false,
    content: @Composable () -> Unit,
) {
    val state = rememberTooltipState()
    val scope = rememberCoroutineScope()

    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Above,
        ),
        tooltip = {
            PlainTooltip {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        state = rememberTooltipState(),
        modifier = modifier
            .then(
                if (showTooltipByShortPress) {
                    Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                scope.launch {
                                    state.show()
                                }
                            },
                        )
                } else {
                    Modifier
                },
            ),
        focusable = focusable,
        enableUserInput = enableUserInput,
        content = content,
    )
}
