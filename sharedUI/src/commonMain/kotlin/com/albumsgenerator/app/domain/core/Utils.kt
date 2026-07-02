package com.albumsgenerator.app.domain.core

object Utils {
    fun slugify(input: String): String {
        return input
            .lowercase()
            .replace(" ", "-") // The Beach Boys
            .replace("'", "") // The B-52's
            .replace(".", "") // B.B. King
            .replace(",", "") // Crosby, Stills & Nash
            .replace("\"", "") // Bonnie "Prince" Billy
            .replace("ö", "o") // Björk
            .replace("ó", "o") // Sigur Rós
            .replace("&", "and") // Simon & Garfunkel
            .replace("/", "") // AC/DC
    }
}
