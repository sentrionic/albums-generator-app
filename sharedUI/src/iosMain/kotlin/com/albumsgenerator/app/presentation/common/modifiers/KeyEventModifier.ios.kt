package com.albumsgenerator.app.presentation.common.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent

actual fun Modifier.onEscape(onEscape: () -> Unit): Modifier = this then Modifier
    .onKeyEvent {
//        if (keyEvent.nativeKeyEvent == NativeKeyEvent.) {
//            onEscape()
//            true
//        } else {
//            false
//        }
        false
    }
