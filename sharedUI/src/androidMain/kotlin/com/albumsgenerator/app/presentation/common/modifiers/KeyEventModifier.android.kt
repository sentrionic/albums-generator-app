package com.albumsgenerator.app.presentation.common.modifiers

import android.view.KeyEvent
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent

actual fun Modifier.onEscape(onEscape: () -> Unit): Modifier = this then Modifier
    .onKeyEvent { keyEvent ->
        if (keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ESCAPE) {
            onEscape()
            true
        } else {
            false
        }
    }
