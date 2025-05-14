package com.hatfat.swccg.json.parse.count

data class UnmatchedLine(
    val line: String,
    val decks: MutableSet<String>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UnmatchedLine) return false

        return line.lowercase() == other.line.lowercase()
    }

    override fun hashCode(): Int {
        return line.lowercase().hashCode()
    }
}
