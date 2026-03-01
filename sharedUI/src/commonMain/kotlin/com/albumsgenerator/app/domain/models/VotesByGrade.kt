package com.albumsgenerator.app.domain.models

data class VotesByGrade(val x1: Int, val x2: Int, val x3: Int, val x4: Int, val x5: Int) {
    val totalVotes by lazy {
        x1 + x2 + x3 + x4 + x5
    }

    val average: Int by lazy {
        x1 + x2 * 2 + x3 * 3 + x4 * 4 + x5 * 5
    }

    companion object {
        val EMPTY = VotesByGrade(
            x1 = 0,
            x2 = 0,
            x3 = 0,
            x4 = 0,
            x5 = 0,
        )
    }
}
