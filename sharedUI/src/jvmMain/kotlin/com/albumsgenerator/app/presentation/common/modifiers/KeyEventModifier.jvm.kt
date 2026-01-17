package com.albumsgenerator.app.presentation.common.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import java.awt.event.KeyEvent

actual fun Modifier.onEscape(onEscape: () -> Unit): Modifier = this then Modifier
    .onKeyEvent { keyEvent ->
        if (keyEvent.nativeKeyEvent == KeyEvent.VK_ESCAPE) {
            onEscape()
            true
        } else {
            false
        }
    }
