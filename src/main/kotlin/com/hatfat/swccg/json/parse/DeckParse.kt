package com.hatfat.swccg.json.parse

import com.hatfat.swccg.json.parse.data.SWCCGDeck
import java.io.File

class DeckParse(
    private val directory: String
) {
    fun parse() {
        println("Decktech Parse")
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

    fun parseFile(deckFile: File) {
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

        parseCards(deck, lines)

    }

    fun parseHeader(deck: SWCCGDeck, lines: List<String>) {
//        println(lines)

    }

    fun parseCards(deck: SWCCGDeck, lines: List<String>) {
        assert(lines[0].startsWith("Cards:"))
        assert(lines[1] == "")
        println(lines)
    }
}