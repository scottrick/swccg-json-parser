package com.hatfat.swccg.json.parse.data.deck

data class SWCCGDeck(
    var author: String?,
    var title: String?,
    var isDark: Boolean,
    var date: String?,
    var description: String?,
    var id: Int?,
    var cards: MutableList<SWCCGDeckEntry>,
    var source: String?,
    var debugSource: String?
) {
    companion object {
        fun create(): SWCCGDeck {
            return SWCCGDeck(null, null, true, null, null, null, mutableListOf(), null, null)
        }
    }

    fun addDeckEntry(entry: SWCCGDeckEntry) {
        cards.find { it.cardId == entry.cardId }?.let {
            if (it.count == entry.count) {
                // entry was already in the list, so ignore the duplicate!
                println("entry: $entry already found!!!")
                return
            }
        }

        cards.add(entry)
    }

    fun cardCount(): Int {
        return cards.map { it.count }.sum()
    }

    fun print(extraInfo: Boolean) {
        println("--------------------------------")
        println("Deck: $title")
        println("Source: $source")
        println("$debugSource")
        println("Side: ${if (isDark) "Dark" else "Light"}")
        println("Card Count: ${cardCount()}")

        if (extraInfo) {
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
}
