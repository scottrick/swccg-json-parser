package com.hatfat.swccg.json.parse

import com.hatfat.swccg.json.parse.data.SWCCGCard
import com.hatfat.swccg.json.parse.data.deck.SWCCGDeck
import com.hatfat.swccg.json.parse.data.deck.SWCCGDeckEntry
import com.hatfat.swccg.json.parse.data.deck.createCorrections
import java.io.File
import java.text.DecimalFormat

class DeckParse(
    private val directory: String,
    private val allCards: List<SWCCGCard>,
) {
    private var processedDarkCards = HashMap<String, SWCCGCard>()
    private var processedLightCards = HashMap<String, SWCCGCard>()

    init {
        processCardNames(allCards)
    }

    private val lightCardNames = processedLightCards.keys
    private val darkCardNames = processedDarkCards.keys
    private val corrections = createCorrections()

    fun parse() {
        println("DeckTech Parse")
        println("${allCards.size} cards loaded")

        var numDecksParsed = 0
        var numCardsParsed = 0

        var currentDeckNum = 1
        val deckToParse = 2

        val dir =
            File(directory).walkTopDown().forEach { file ->
                if (file.extension == "md" && numDecksParsed < 2) {
                    if (currentDeckNum == deckToParse) {
                        val deck = parseFile(file)
                        numCardsParsed += deck.cardCount()
                        numDecksParsed++
                    }

                    currentDeckNum++
                }
            }

        val percent = numCardsParsed.toFloat() / (numDecksParsed.toFloat() * 60f) * 100f
        val percentFormatter = DecimalFormat("#.##")

        println("Parsed $numDecksParsed decks.")
        println("Found $numCardsParsed/${numDecksParsed * 60} cards (${percentFormatter.format(percent)}%).")
    }

    private fun parseFile(deckFile: File): SWCCGDeck {
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

        deck.source = deckFile.canonicalFile.absolutePath

        // Parse header
        val endOfHeaderIndex = lines.indexOfFirst { it == "---" }
        parseHeader(deck, lines.subList(0, endOfHeaderIndex))

        // Remove header
        lines = lines.drop(endOfHeaderIndex + 1)

        // Find start of strategy section
        val strategyLine =
            lines.find { line ->
                line.contains("Strategy:")
            }
        val strategyIndex = lines.indexOf(strategyLine)
        assert(strategyIndex != -1)

        val cardLines = lines.subList(0, strategyIndex)

        parseCards(deck, cardLines)

        deck.print()

        return deck
    }

    private fun parseHeader(
        deck: SWCCGDeck,
        lines: List<String>,
    ) {
        for (line in lines) {
            var processedLine = line.trim()

            // Deck Title
            if (processedLine.startsWith("title: !")) {
                processedLine = processedLine.substring(8).trim()

                if (processedLine.startsWith("\"")) {
                    processedLine = processedLine.substring(1)
                }
                if (processedLine.endsWith("\"")) {
                    processedLine = processedLine.substring(0, processedLine.length - 1)
                }

                deck.title = processedLine
            }

            if (processedLine.startsWith("side:")) {
                val sideLine = processedLine.substring(5).trim().toLowerCase()
                if (sideLine == "light" || sideLine == "ls") {
                    deck.isDark = false
                }
            }
        }
        println(lines)
    }

    private fun parseCards(
        deck: SWCCGDeck,
        rawLines: List<String>,
    ) {
        assert(rawLines[0].startsWith("Cards:"))
        assert(rawLines[1] == "")
        val lines = rawLines.drop(2)

        val unmatchedLines = mutableListOf<String>()

        for (line in lines) {
//            println("RAW: $line")
            var processedLine = getProcessedCardName(line, false).trim()

            if (line.trim().isBlank() || line.trim().isEmpty() || line == "'") {
                continue
            }

            // Before any fancy processing, if the card matches, add it straight away
            if (addIfExactMatch(deck, processedLine, 1, false)) {
                continue
            }

            // Check and correct common errors
            processedLine = checkForCorrections(processedLine)

            // Find card count
            val cardCountResult = getCardCount(processedLine)
            processedLine = cardCountResult.first.trim()
            val count = cardCountResult.second

            // Find is starting card
            val startingCardResult = getIsStartingCard(processedLine)
            processedLine = startingCardResult.first.trim()
            val isStarting = startingCardResult.second

            processedLine = getProcessedCardName(processedLine, true).trim()

            // Check and correct common errors again without spaces
            processedLine = checkForCorrections(processedLine)

            // Now check again with the processed card name for an exact match
            if (addIfExactMatch(deck, processedLine, count, isStarting)) {
                continue
            }

            unmatchedLines.add(processedLine)
            println("NO MATCH: $processedLine")
        }
    }

    private fun addIfExactMatch(deck: SWCCGDeck, cardLine: String, count: Int, isStarting: Boolean): Boolean {
        val cardNames = if (deck.isDark) darkCardNames else lightCardNames
        val processedCards = if (deck.isDark) processedDarkCards else processedLightCards

        val initialExactMatch = checkForExactMatch(cardLine, cardNames)
        initialExactMatch?.let {
            // Have an exact match, so add one copy to the deck and move on.
            deck.addDeckEntry(
                SWCCGDeckEntry(
                    processedCards[it]?.front?.title ?: "??????? null",
                    count,
                    isStarting,
                    null
                )
            )

            return true
        }

        return false
    }

    private fun checkForExactMatch(cardLine: String, cardNames: Set<String>): String? {
        val processedLine = getProcessedCardName(cardLine, true).trim()

        // Try to find an exact match
        val exact =
            cardNames.filter { cardName ->
                cardName.equals(processedLine, true)
            }

        if (exact.size == 1) {
            // found exactly one card that matches
            return exact[0]
        }

        return null
    }

    private fun checkForCorrections(cardLine: String): String {
        var updatedLine = cardLine

        corrections.forEach { correction ->
            updatedLine = updatedLine.replace(correction.key, correction.value)
        }

        return updatedLine
    }

    fun processCardNames(cards: List<SWCCGCard>) {
        val processedDarkCardNames = HashMap<String, SWCCGCard>()
        val processedLightCardNames = HashMap<String, SWCCGCard>()

        for (card in cards) {
            // Ignore virtual cards for now
            var set = 0
            set = if (card.set?.contains("d") == true) {
                0
            } else {
                card.set?.toInt() ?: 0
            }
            if (set >= 200) {
                continue
            }

            card.front.title?.let { title ->
                val processedTitle = getProcessedCardName(title, true)
                if (card.side?.toLowerCase() == "dark") {
                    processedDarkCardNames[processedTitle] = card
                } else {
                    processedLightCardNames[processedTitle] = card
                }
            }
        }

        this.processedDarkCards = processedDarkCardNames
        this.processedLightCards = processedLightCardNames
    }
}


// Gets starting, and removes starting from string.
fun getIsStartingCard(cardName: String): Pair<String, Boolean> {
    var processedCardName = cardName

    return Pair(processedCardName, false)
}

// Gets card count, and removes card count from string.
fun getCardCount(startingCardLine: String): Pair<String, Int> {
    var count = 1
    var cardLine = startingCardLine

    val numXStartRegex = Regex("\\d+x")
    val xNumEndRegex = Regex("\\b x\\d+")
    val startingNumRegex = Regex("^\\d+ \\b")

    // See if the line starts with #x
    numXStartRegex.find(cardLine)?.let { matchResult ->
        val xIndex = cardLine.indexOf("x")
        var countString = cardLine.substring(matchResult.range.start, xIndex)
        count = countString.toInt()
        cardLine = cardLine.removeRange(matchResult.range)
    }

    // See if the line ends with " x#"
    if (xNumEndRegex.containsMatchIn(cardLine)) {
        val xIndex = cardLine.lastIndexOf(" x")
        var countString = cardLine.substring(xIndex + 2)

        var endDigitIndex = 1
        while (endDigitIndex < countString.length && !countString[endDigitIndex - 1].isDigit()) {
            endDigitIndex++
        }

        // CHOP off the end
        countString = countString.substring(0, endDigitIndex).trim()

        count = countString.toInt()
        cardLine = cardLine.substring(0, xIndex)
    }

    // See if the line starts with #_CardName
    startingNumRegex.find(cardLine)?.let { matchResult ->
        println("found # num: $cardLine")
        count = cardLine.substring(matchResult.range).trim().toInt()
        cardLine = cardLine.removeRange(matchResult.range)
        println("$count after: $cardLine")
    }

    return Pair(cardLine, count)
}

fun getProcessedCardName(
    cardName: String,
    shouldRemoveSpacesAndParens: Boolean,
): String {
    var processedTitle = cardName.toLowerCase()

    val stringsToRemove = mutableListOf<String>()
    stringsToRemove.add("'")
    stringsToRemove.add("`")
    stringsToRemove.add("’")
    stringsToRemove.add(":")
    stringsToRemove.add(";")
    stringsToRemove.add("•")
    stringsToRemove.add(".")
    stringsToRemove.add(",")
    stringsToRemove.add("&")
    stringsToRemove.add("\\")
    stringsToRemove.add("/")
    stringsToRemove.add("-")
    stringsToRemove.add("=")
    stringsToRemove.add("?")
    stringsToRemove.add("!")
    stringsToRemove.add("@")

    if (shouldRemoveSpacesAndParens) {
        stringsToRemove.add(" ")
    }

    for (stringToRemove in stringsToRemove) {
        while (processedTitle.indexOf(stringToRemove, 0, true) != -1) {
            val index = processedTitle.indexOf(stringToRemove, 0, true)
            processedTitle = processedTitle.removeRange(index, index + 1)
        }
    }

    if (shouldRemoveSpacesAndParens) {
        // Remove anything after an opening parenthesis
        val parenIndex = processedTitle.indexOf("(")
        if (parenIndex >= 0) {
            processedTitle = processedTitle.substring(0, parenIndex)
        }
    }

    return processedTitle
}
