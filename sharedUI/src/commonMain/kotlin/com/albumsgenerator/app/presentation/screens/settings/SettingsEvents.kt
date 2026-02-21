package com.albumsgenerator.app.presentation.screens.settings

import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.core.Theme

sealed interface SettingsEvents {
    data class UpdateStreamingService(val service: StreamingServices) : SettingsEvents
    data class UpdateTheme(val theme: Theme) : SettingsEvents
    data class UpdateSpoilerMode(val spoilerFree: Boolean) : SettingsEvents
    data object Refresh : SettingsEvents
    data object Logout : SettingsEvents
    data object ResetMessage : SettingsEvents
}
