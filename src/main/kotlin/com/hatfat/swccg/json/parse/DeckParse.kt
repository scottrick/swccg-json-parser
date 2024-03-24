package com.hatfat.swccg.json.parse

import com.hatfat.swccg.json.parse.data.SWCCGCard
import com.hatfat.swccg.json.parse.data.SWCCGDeck
import java.io.File

class DeckParse(
    private val directory: String, private val allCards: List<SWCCGCard>
) {
    private val processedCards = processCardNames(allCards)
    private val cardNames = processedCards.keys

    fun parse() {
        println("DeckTech Parse")
        println("${allCards.size} cards loaded")

        var count = 0

        val dir = File(directory).walkTopDown().forEach { file ->
            if (file.extension == "md" && count < 4000) {
//                println("found deck: " + file.name)
                parseFile(file)
                count++
            }
        }

        println("Parsed $count decks.")
    }

    private fun parseFile(deckFile: File) {
//        ---
//        layout: deck
//        author: ! Mike "Iceman" Fitzgerald
//        title: ! "Copperhead-Big Blue in a Ralltiir form"
//        side: Dark
//        date: 1999-12-02
//        description: ! "This is a big blue deck that I've beentrying to incorporate into various objectives and fits nicely with Ralltiir Operations."
//        rating: 4.5
//        id: 1512
//        permalink: "/starwarsccg/deck/1512/"
//        ---

        val mutableLines = mutableListOf<String>()

        deckFile.bufferedReader().forEachLine { line ->
            mutableLines.add(line)
//            println(line)
        }

        var lines: List<String> = mutableLines
        // Delete the first line which is always "---"
        assert(lines[0] == "---")
        lines = lines.drop(1)

        val deck = SWCCGDeck.create()

        // Parse header
        val endOfHeaderIndex = lines.indexOfFirst { it == "---" }
        parseHeader(deck, lines.subList(0, endOfHeaderIndex))

        // Remove header
        lines = lines.drop(endOfHeaderIndex + 1)

        // Find start of strategy section
        val strategyLine = lines.find { line ->
            line.contains("Strategy:")
        }
        val strategyIndex = lines.indexOf(strategyLine)
        assert(strategyIndex != -1)

        val cardLines = lines.subList(0, strategyIndex)

        parseCards(deck, cardLines)
    }

    private fun parseHeader(deck: SWCCGDeck, lines: List<String>) {
//        println(lines)

    }

    private fun parseCards(deck: SWCCGDeck, rawLines: List<String>) {
        assert(rawLines[0].startsWith("Cards:"))
        assert(rawLines[1] == "")
        val lines = rawLines.drop(2)
//        println(lines)

//        val headerRegex = "/\\w+/gm".toRegex()
        val headerRegex = """\w+""".toRegex()
        val reg = Regex("""[0-9]*""")
        val xRegex = Regex("\\b x\\d+")

        val cards = mutableMapOf<String, Int>()
        val unmatchedLines = mutableListOf<String>()

        var totalCardCount = 0

        for (line in lines) {
            var processedLine = getProcessedCardName(line, false).trim()

            if (line.trim().isBlank() || line.trim().isEmpty() || line == "'") {
                continue
            }


//            val words = headerRegex.findAll(line).toList(c)
//            if (words.isNotEmpty() && words[0].value.trim().startsWith("start", true)) {
//                println("found STARTING CARDS! $line")
//            }

            var count = 1

            // See if the line ends with " x#"
            if (xRegex.containsMatchIn(processedLine)) {
                val xIndex = processedLine.lastIndexOf(" x")
                var countString = processedLine.substring(xIndex + 2)

                var endDigitIndex = 1
                while (endDigitIndex < countString.length && !countString[endDigitIndex - 1].isDigit()) {
                    endDigitIndex++
                }

                // CHOP off the end
                countString = countString.substring(0, endDigitIndex).trim()

//                if (countString.contains(" ")) {
//                    var digitIndex = 0
//                    while (countString[digitIndex].isDigit()) {
//                        digitIndex++
//                    }
//                }

                count = countString.toInt()
                processedLine = processedLine.substring(0, xIndex)
            }

            processedLine = getProcessedCardName(processedLine, true).trim()

            // First try to find an exact match
            val exact = cardNames.filter { cardName ->
                cardName.equals(processedLine, true)
            }

            if (exact.size == 1) {
                // found one card that matches, add it to the deck
                cards[(exact[0])] = count
                totalCardCount += count
                continue
            }

            unmatchedLines.add(processedLine)
//            println("NO MATCH: $processedLine")

//            val filtered = partialContains(processedLine)
//            if (filtered.isNotEmpty()) {
//                println("$line [$processedLine] filtered ${filtered.size}, matches: $filtered")
//            }
        }

//        var count = 0
//        for (card in cards.keys) {
//            println("  ${cards[card]}x $card")
//            count += cards[card] ?: 0
//        }

        println("Finished parsing, found $totalCardCount cards.")
    }

    fun partialContains(line: String): List<String> {
        return cardNames.filter { cardName ->
            cardName.contains(line, true) || line.contains(cardName, true)
        }
    }
}

fun processCardNames(cards: List<SWCCGCard>): HashMap<String, SWCCGCard> {
    val processedCardNames = HashMap<String, SWCCGCard>()

    for (card in cards) {
        card.front.title?.let { title ->
            val processedTitle = getProcessedCardName(title, true)
//            println("$title -> $processedTitle")
            processedCardNames[processedTitle] = card
        }
    }

    return processedCardNames
}

fun getProcessedCardName(cardName: String, shouldRemoveSpaces: Boolean): String {
    var processedTitle = cardName.toLowerCase()

    val stringsToRemove = mutableListOf<String>();
    stringsToRemove.add("'")
    stringsToRemove.add(":")
    stringsToRemove.add(";")
    stringsToRemove.add("•")
    stringsToRemove.add(".")
    stringsToRemove.add(",")
    stringsToRemove.add("&")
    stringsToRemove.add("\\")
    stringsToRemove.add("/")

    if (shouldRemoveSpaces) {
        stringsToRemove.add(" ")
    }

    for (stringToRemove in stringsToRemove) {
        while (processedTitle.indexOf(stringToRemove, 0, true) != -1) {
            val index = processedTitle.indexOf(stringToRemove, 0, true)
            processedTitle = processedTitle.removeRange(index, index + 1)
        }
    }

    return processedTitle
}
