package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.core.Theme

@Immutable
data class UserData(
    val projectName: String?,
    val theme: Theme,
    val service: StreamingServices?,
    val spoilerFree: Boolean,
) {
    companion object {
        val EMPTY = UserData(
            projectName = null,
            theme = Theme.SYSTEM,
            service = null,
            spoilerFree = true,
        )
    }
}
