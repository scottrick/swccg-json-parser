package com.hatfat.swccg.json.parse

import com.hatfat.swccg.json.parse.data.SWCCGCard
import com.hatfat.swccg.json.parse.data.deck.*
import java.io.File
import java.text.DecimalFormat

class DeckParse(
    private val directory: String,
    private val allCards: List<SWCCGCard>,
) {
    private val redFlagList = createRedFlagList()
    private val expectedDeckCounts = createExpectedDeckSizeCounts()
    private var processedDarkCards = HashMap<String, SWCCGCard>()
    private var processedLightCards = HashMap<String, SWCCGCard>()

    private val hexHtmlRegEx = Regex("&#\\d*;")

    init {
        processCardNames(allCards)
    }

    private val lightCardNames = processedLightCards.keys
    private val darkCardNames = processedDarkCards.keys
    private val lightGeneralCorrections = createLightGeneralCorrections()
    private val lightExactCorrections = createLightExactCorrections()
    private val lightWildcardCorrections = createLightWildcardCorrections()
    private val lightSplitCorrections = createLightSplitCorrections()
    private val darkGeneralCorrections = createDarkGeneralCorrections()
    private val darkExactCorrections = createDarkExactCorrections()
    private val darkWildcardCorrections = createDarkWildcardCorrections()
    private val darkSplitCorrections = createDarkSplitCorrections()

    fun parse() {
        println("DeckTech Parse")
        println("${allCards.size} cards loaded")

        var currentDeckNum = 0
        val deckToParse = 446
        val parseSingleDeck = false
        val verbose = true

        val decks = mutableListOf<SWCCGDeck>()

        File(directory).walkTopDown().forEach { file ->
            if (file.extension == "md" && decks.size < 1000) {
                if (checkForRedFlags(file, false)) {
                    // skipping due to red flags
                } else {
                    if (!parseSingleDeck || currentDeckNum == deckToParse) {
                        decks.add(parseFile(file, verbose && parseSingleDeck))
                    }

                    currentDeckNum++
                }
            }
        }

        // First 100 decks
        // Counted 4446 cards
        // 4461, (12 decks)
        // 4511, (13 decks)
        // 4556, (14 decks)
        // 4580, (16 decks)
        // 4700, (21 decks)
        // 4842, (24 decks)
        // 5115, (30 decks)
        // 5269, (40 decks)

        println("---------------------------------------")
        println("Finished!  Parsed ${decks.size} decks.")
        println("Total cards counted: ${decks.sumBy { it.cardCount() }}")
        println("---------------------------------------")
        if (verbose && !parseSingleDeck) {
            printDeckCountSummaries(decks, 10)
            println("---------------------------------------")
        }

        if (!parseSingleDeck) {
            for (index in expectedDeckCounts.indices) {
                val deck = decks[index]

                if (expectedDeckCounts[index] != deck.cardCount()) {
                    println("VALIDATION FAILURE [$index]: ${deck.debugSource}")
                }
            }
        }
    }

    // Filter out any decks that contain red flag words
    private fun checkForRedFlags(deckFile: File, verbose: Boolean): Boolean {
        for (line in deckFile.bufferedReader().lines()) {
            for (redFlag in redFlagList) {
                if (line.contains(redFlag, true)) {
                    if (verbose) {
                        println("file: ${deckFile.canonicalFile.absolutePath}")
                        println("RedFlag > $line")
                    }

                    return true
                }
            }
        }

        return false
    }

    private fun parseFile(deckFile: File, verbose: Boolean): SWCCGDeck {
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
            // filter out random HTML hex codes
            var filteredLine = line

            hexHtmlRegEx.find(filteredLine)?.let { matchResult ->
                filteredLine = filteredLine.removeRange(matchResult.range)
            }

            mutableLines.add(filteredLine)
//            println(line)
        }

        var lines: List<String> = mutableLines
        // Delete the first line which is always "---"
        assert(lines[0] == "---")
        lines = lines.drop(1)

        val deck = SWCCGDeck.create()

        deck.source = deckFile.name
        deck.debugSource = deckFile.canonicalFile.absolutePath

        // Parse header
        val endOfHeaderIndex = lines.indexOfFirst { it == "---" }
        parseHeader(deck, lines.subList(0, endOfHeaderIndex))

        // Remove header
        lines = lines.drop(endOfHeaderIndex + 1)

        // Find start of strategy section
        val strategyLine =
            lines.find { line ->
                line.trim().startsWith("strategy", true)
            }
        val strategyIndex = lines.indexOf(strategyLine)
        assert(strategyIndex != -1)

        val cardLines = lines.subList(0, strategyIndex)

        parseCards(deck, cardLines, verbose)

        if (verbose) {
            deck.print(true)
        }

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
    }

    private fun parseCards(
        deck: SWCCGDeck,
        rawLines: List<String>,
        verbose: Boolean
    ) {
        assert(rawLines[0].startsWith("Cards:"))
        assert(rawLines[1] == "")
        val lines = rawLines.drop(2)

        val unmatchedLines = mutableListOf<String>()

        for (line in lines) {
            var processedLine = getProcessedCardName(line, false).trim()

            if (line.trim().isBlank() || line.trim().isEmpty() || line == "'") {
                continue
            }

            // Before any fancy processing, if the card matches, add it straight away
            if (addIfExactMatch(deck, processedLine, 1, false)) {
                continue
            }

            // check for multiple cards on the same line
            val splitLines = checkForCardSplits(deck, processedLine)

            for (splitLine in splitLines) {
                // Find card count
                var cardCountResult = getCardCount(splitLine, false)
                processedLine = cardCountResult.first.trim()
                var count = cardCountResult.second

                // Check and correct common errors
                processedLine = checkForCorrections(deck, processedLine, false)

                // Find is starting card
                val startingCardResult = getIsStartingCard(processedLine)
                processedLine = startingCardResult.first.trim()
                val isStarting = startingCardResult.second

                processedLine = getProcessedCardName(processedLine, true).trim()

                // Check and correct common errors again without spaces
                processedLine = checkForCorrections(deck, processedLine, true)

                // Now check again with the processed card name for an exact match
                if (addIfExactMatch(deck, processedLine, count, isStarting)) {
                    continue
                }

                // Still no match?  Check if this line has two cards next to each other
                if (checkForTwoCards(deck, processedLine, isStarting, verbose)) {
                    continue
                }

                if (count == 1) {
                    // try finding count again, with extra searches
                    cardCountResult = getCardCount(processedLine, true)
                    processedLine = cardCountResult.first.trim()
                    count = cardCountResult.second
                }

                // Now check again with the processed card name for an exact match
                if (addIfExactMatch(deck, processedLine, count, isStarting)) {
                    continue
                }

                unmatchedLines.add(processedLine)
                if (verbose) {
                    println("NO MATCH: $processedLine")
                }
            }
        }
    }

    private fun addIfExactMatch(deck: SWCCGDeck, cardLine: String, count: Int, isStarting: Boolean): Boolean {
        val cardNames = if (deck.isDark) darkCardNames else lightCardNames
        val processedCards = if (deck.isDark) processedDarkCards else processedLightCards

        val initialExactMatch = checkForExactMatch(cardLine, cardNames)
        initialExactMatch?.let {
            val card = processedCards[it]
            // Have an exact match, so add one copy to the deck and move on.
            deck.addDeckEntry(
                SWCCGDeckEntry(
                    card?.front?.title ?: "NULL TITLE",
                    count,
                    isStarting,
                    card?.id ?: -1
                )
            )

            return true
        }

        return false
    }

    private fun checkForTwoCards(deck: SWCCGDeck, cardLine: String, isStarting: Boolean, verbose: Boolean): Boolean {
        val cardNames = if (deck.isDark) darkCardNames else lightCardNames

        val result = cardNames.filter {
            cardLine.startsWith(it)
        }

        if (result.size == 1) {
            val firstCardLine = result[0]
            val secondCardLine = cardLine.removePrefix(result[0])

            if (checkForExactMatch(firstCardLine, cardNames) != null &&
                checkForExactMatch(secondCardLine, cardNames) != null
            ) {
                // There are two exact matches!
                // Just assume they are both count 1, since we don't know which card the count is associated with.
                addIfExactMatch(deck, firstCardLine, 1, isStarting)
                addIfExactMatch(deck, secondCardLine, 1, isStarting)

                if (verbose) {
                    println("CHECKING FOR TWO CARDS, FOUND SINGLE MATCH FOR BOTH:")
                    println("  $cardLine")
                    println("  1st: $firstCardLine")
                    println("  2nd: $secondCardLine")
                }

                return true
            }
        }

        return false
    }

    private fun checkForExactMatch(cardLine: String, cardNames: Set<String>): String? {
        // Try to find an exact match
        val exact =
            cardNames.filter { cardName ->
                cardName.equals(cardLine, true)
            }

        if (exact.size == 1) {
            // found exactly one card that matches
            return exact[0]
        }

        return null
    }

    // Check if the line should be split into multiple card lines
    private fun checkForCardSplits(deck: SWCCGDeck, cardLine: String): List<String> {
        // IE, Red Squadron 1, 4, 7 -> three cards
        val splitCorrections = if (deck.isDark) darkSplitCorrections else lightSplitCorrections

        return splitCorrections[cardLine] ?: listOf(cardLine)
    }

    private fun checkForCorrections(deck: SWCCGDeck, cardLine: String, includeWildcard: Boolean): String {
        var updatedLine = cardLine

        val generalCorrections = if (deck.isDark) darkGeneralCorrections else lightGeneralCorrections
        val exactCorrections = if (deck.isDark) darkExactCorrections else lightExactCorrections
        val wildcardCorrections = if (deck.isDark) darkWildcardCorrections else lightWildcardCorrections

        // general corrections just can match any part of the cardName
        generalCorrections.forEach { correction ->
            updatedLine = updatedLine.replace(correction.key, correction.value)
        }

        // exact corrections must match the entire line exactly
        exactCorrections.forEach { correction ->
            updatedLine = if (updatedLine == correction.key) correction.value else updatedLine
        }

        if (includeWildcard) {
            // if any part of the wildcard correction matches, replace the entire line
            wildcardCorrections.forEach { correction ->
                if (updatedLine.contains(correction.key)) {
                    updatedLine = correction.value
                }
            }
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
                // Virtual Defensive Shields
                200
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
    return Pair(cardName, false)
}

// Gets card count, and removes card count from string.
fun getCardCount(startingCardLine: String, extraSearches: Boolean): Pair<String, Int> {
    var count = 1
    var cardLine = startingCardLine

    val numXStartRegex = Regex("^\\d+ *x")
    val xNumStartRegex = Regex("^x *\\d+ ")
    val xNumEndRegexNoSpace = Regex("\\Sx *\\d+")
    val xNumEndRegexWithSpace = Regex("\\S x *\\d+")
    val xNumParenEndRegex = Regex("\\b *\\(\\d+")
    val startingNumRegex = Regex("^\\d+ *\\b")
    val endingNumRegex = Regex("\\B\\D *\\d$")

    val debug = false
    if (debug) {
        println("cardline: $cardLine")
    }

    // See if the line starts with #x
    numXStartRegex.find(cardLine)?.let { matchResult ->
        count = cardLine.substring(matchResult.range.start, matchResult.range.endInclusive).trim().toInt()
        cardLine = cardLine.removeRange(matchResult.range)

        if (debug) {
            println("count(1): $count")
            println("cardline: $cardLine")
        }

        return Pair(cardLine, count)
    }

    // See if the line starts with #x
    xNumStartRegex.find(cardLine)?.let { matchResult ->
        count = cardLine.substring(matchResult.range.start + 1, matchResult.range.endInclusive).trim().toInt()
        cardLine = cardLine.removeRange(matchResult.range)

        if (debug) {
            println("count(7): $count")
            println("cardline: $cardLine")
        }

        return Pair(cardLine, count)
    }

    // See if the line ends with " x#"
    xNumEndRegexWithSpace.find(cardLine)?.let { matchResult ->
        count = cardLine.substring(matchResult.range).trim().substring(3).trim().toInt()
        cardLine = cardLine.substring(0, matchResult.range.first + 1)

        if (debug) {
            println("count(2): $count")
            println("cardline: $cardLine")
        }

        return Pair(cardLine, count)
    }

    // See if the line ends with "x#"
    xNumEndRegexNoSpace.find(cardLine)?.let { matchResult ->
        count = cardLine.substring(matchResult.range).trim().substring(2).trim().toInt()
        cardLine = cardLine.substring(0, matchResult.range.first)

        if (debug) {
            println("count(3): $count")
            println("cardline: $cardLine")
        }

        return Pair(cardLine, count)
    }

    // See if the line ends with " (x"
    xNumParenEndRegex.find(cardLine)?.let { matchResult ->
        count = cardLine.substring(matchResult.range).trim().substring(1).trim().toInt()
        cardLine = cardLine.substring(0, matchResult.range.first)

        if (debug) {
            println("count(4): $count")
            println("cardline: $cardLine")
        }

        return Pair(cardLine, count)
    }

    // See if the line starts with #_CardName
    startingNumRegex.find(cardLine)?.let { matchResult ->
        count = cardLine.substring(matchResult.range).trim().toInt()
        cardLine = cardLine.removeRange(matchResult.range)

        if (debug) {
            println("count(5): $count")
            println("cardline: $cardLine")
        }

        return Pair(cardLine, count)
    }

    if (extraSearches) {
        // See if the line ends with CardName_#
        endingNumRegex.find(cardLine)?.let { matchResult ->
            count = cardLine.substring(matchResult.range).trim().substring(1).toInt()
            cardLine = cardLine.substring(0, matchResult.range.start + 1)

            if (debug) {
                println("count(6): $count")
                println("cardline: $cardLine")
            }

            return Pair(cardLine, count)
        }
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
    stringsToRemove.add("\t")
    stringsToRemove.add("\\")
    stringsToRemove.add("/")
    stringsToRemove.add("-")
    stringsToRemove.add("=")
    stringsToRemove.add("?")
    stringsToRemove.add("!")
    stringsToRemove.add("@")
    stringsToRemove.add("<")
    stringsToRemove.add(">")
    stringsToRemove.add("#")
    stringsToRemove.add("8217")

    if (shouldRemoveSpacesAndParens) {
        stringsToRemove.add(" ")
    }

    for (stringToRemove in stringsToRemove) {
        while (processedTitle.indexOf(stringToRemove, 0, true) != -1) {
            val index = processedTitle.indexOf(stringToRemove, 0, true)
            processedTitle = processedTitle.removeRange(index, index + stringToRemove.length)
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

private fun printDeckCountSummaries(decks: List<SWCCGDeck>, numPerLine: Int) {
    val countFormatter = DecimalFormat("##")
    val lineFormatter = DecimalFormat("000")

    for (i in 0 until decks.size / numPerLine) {
        var line = "${lineFormatter.format(i)}] "
        for (column in 0 until numPerLine) {
            val index = i * numPerLine + column
            if (index < decks.size) {
                val countValue = decks[index].cardCount()

                if (countValue > 80) {
                    println("$index] LARGE DECK: ${decks[index].debugSource}")
                }
                if (countValue <= 9) {
                    println("$index] TINY DECK: ${decks[index].debugSource}")
                }

                if (column != 0) {
                    line += ", "
                }

                if (countValue < 10) {
                    line += " "
                }

                line += countFormatter.format(countValue)
            }
        }

        println(line)
    }
}


private fun printDeckPercentSummaries(decks: List<SWCCGDeck>, numPerLine: Int) {
    val percentFormatter = DecimalFormat("##%")
    val lineFormatter = DecimalFormat("000")

    for (i in 0 until decks.size / numPerLine) {
        var line = "${lineFormatter.format(i)}] "
        for (column in 0 until numPerLine) {
            val index = i * numPerLine + column
            if (index < decks.size) {
                val percentValue = decks[index].cardCount().toFloat() / 60f

                if (column != 0) {
                    line += ", "
                }

                if (percentValue < 1f) {
                    line += " "
                }

                if (percentValue <= 0f) {
                    line += " "
                }

                line += percentFormatter.format(percentValue)
            }
        }

        println(line)
    }
}
