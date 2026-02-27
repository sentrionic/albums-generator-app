package com.albumsgenerator.app.domain.models

enum class SpoilerMode(val label: String, val description: String) {
    VISIBLE(
        label = "Visible",
        description = "Default. Matches the website",
    ),
    PARTIAL(
        label = "Partial",
        description = "Hides future album names and artists.",
    ),
    HIDDEN(
        label = "Hidden",
        description = "Only display already generated albums.",
    ),
    ;

    val isOn get() = this != VISIBLE
}
