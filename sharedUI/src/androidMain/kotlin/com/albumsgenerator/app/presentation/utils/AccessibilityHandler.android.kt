package com.albumsgenerator.app.presentation.utils

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

private val Context.isScreenReaderOn: Boolean
    get() {
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager ?: return false
        return if (accessibilityManager.isEnabled) {
            val serviceInfoList = accessibilityManager.getEnabledAccessibilityServiceList(
                AccessibilityServiceInfo.FEEDBACK_SPOKEN,
            )
            serviceInfoList.isNotEmpty()
        } else {
            false
        }
    }

@Composable
actual fun collectIsScreenReaderEnabledAsState(): State<Boolean> {
    val context = LocalContext.current

    val accessibilityManager =
        context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager

    val screenReaderEnabled = remember {
        mutableStateOf(context.isScreenReaderOn)
    }

    var accessibilityEnabled by remember {
        mutableStateOf(accessibilityManager?.isEnabled ?: false)
    }

    accessibilityManager?.addTouchExplorationStateChangeListener {
        accessibilityEnabled = it
    }

    LaunchedEffect(accessibilityEnabled) {
        screenReaderEnabled.value = if (accessibilityEnabled) {
            context.isScreenReaderOn
        } else {
            false
        }
    }

    return screenReaderEnabled
}
