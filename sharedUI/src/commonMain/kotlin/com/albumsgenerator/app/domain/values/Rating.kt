package com.albumsgenerator.app.domain.values

import com.albumsgenerator.app.domain.models.History
import kotlin.jvm.JvmInline

private val validValues = setOf("1", "2", "3", "4", "5", History.SKIPPED_TAG)

@JvmInline
value class Rating(val value: String) {
    init {
        require(value in validValues) { "Invalid rating. Value must be in range $validValues" }
    }
}
