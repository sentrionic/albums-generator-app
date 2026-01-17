package com.albumsgenerator.app.presentation.utils

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

actual fun formatLocalDate(date: LocalDate): String = date.toJavaLocalDate().format(
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM),
)
