package com.hatfat.swccg.json.parse.data.deck

data class SWCCGDeck(
    var author: String?,
    var title: String?,
    var isDark: Boolean,
    var date: String?,
    var description: String?,
    var id: Int?,
    var cards: MutableList<SWCCGDeckEntry>,
    var source: String?
) {
    companion object {
        fun create(): SWCCGDeck {
            return SWCCGDeck(null, null, true, null, null, null, mutableListOf(), null)
        }
    }

    fun addDeckEntry(entry: SWCCGDeckEntry) {
        cards.add(entry)
    }

    fun cardCount(): Int {
        return cards.map { it.count }.sum()
    }

    fun print() {
        println("Deck: $title")
        println("Source: $source")
        println("Side: ${if (isDark) "Dark" else "Light"}")
        println("Starting")
        println("--------------------------------")
        for (entry in cards) {
            if (entry.isStarting) {
                println("${entry.count}x ${entry.cardName}")
            }
        }
        println("General")
        println("--------------------------------")
        for (entry in cards) {
            if (!entry.isStarting) {
                println("${entry.count}x ${entry.cardName}")
            }
        }
    }
}
