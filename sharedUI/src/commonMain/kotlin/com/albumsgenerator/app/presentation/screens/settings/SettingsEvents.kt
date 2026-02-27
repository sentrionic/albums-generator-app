package com.albumsgenerator.app.presentation.screens.settings

import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.core.Theme
import com.albumsgenerator.app.domain.models.SpoilerMode

sealed interface SettingsEvents {
    data class UpdateStreamingService(val service: StreamingServices) : SettingsEvents
    data class UpdateTheme(val theme: Theme) : SettingsEvents
    data class UpdateSpoilerMode(val spoilerMode: SpoilerMode) : SettingsEvents
    data object Refresh : SettingsEvents
    data object Logout : SettingsEvents
    data object ResetMessage : SettingsEvents
}
