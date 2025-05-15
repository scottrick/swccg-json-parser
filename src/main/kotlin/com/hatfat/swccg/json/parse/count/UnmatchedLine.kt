package com.hatfat.swccg.json.parse.count

import com.hatfat.swccg.json.parse.data.deck.SWCCGDeck

data class UnmatchedLine(
    val processedLine: String,
    val lines: MutableSet<String>,
    val decks: MutableSet<SWCCGDeck>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UnmatchedLine) return false

        return processedLine == other.processedLine
    }

    override fun hashCode(): Int {
        return processedLine.hashCode()
    }
}
