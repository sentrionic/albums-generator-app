package com.albumsgenerator.app.presentation.utils

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) {
        it.titlecase()
    } else {
        it.toString()
    }
}
