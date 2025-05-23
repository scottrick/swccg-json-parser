package com.hatfat.swccg.json.parse.count

import com.hatfat.swccg.json.parse.data.deck.SWCCGDeck
import com.hatfat.swccg.json.parse.data.deck.createIgnoreExactFilters
import com.hatfat.swccg.json.parse.data.deck.createIgnoreUnmatchedWildcards
import java.text.DecimalFormat

class UnmatchedRegistry {
    val registry = HashMap<String, UnmatchedLine>()
    val ignoreWildcards = createIgnoreUnmatchedWildcards()
    val ignoreExact = createIgnoreExactFilters()

    fun registerUnmatchedLine(line: String, processedLine: String, deck: SWCCGDeck) {
        registry.getOrPut(processedLine) {
            UnmatchedLine(processedLine, HashSet(), HashSet())
        }.also {
            it.decks.add(deck)
            it.lines.add(line)
        }
    }

    fun shouldSkipInSummary(line: String, verbose: Boolean = false): Boolean {
        for (filter in ignoreWildcards) {
            if (line.contains(filter, true)) {
                if (verbose) {
                    println("skipping: $line")
                }
                return true
            }
        }
        for (filter in ignoreExact) {
            if (line.contains(filter, true)) {
                if (verbose) {
                    println("skipping: $line")
                }
                return true
            }
        }

        return false
    }

    fun printSummary(verbose: Boolean = false) {
        println("---------------------------------------")
        println("UnmatchedRegistry Summary")
        println("Total entries: ${registry.keys.size}")
        println("---------------------------------------")

        if (verbose) {
            val countFormatter = DecimalFormat("000")
            val printDecks = true

            val numToPrint = 20
            val printDeckSize = 20
            var currentNum = 0
            var skipped = 0
            val list = registry.values.sortedByDescending { it.decks.size }

            for (line in list) {
                if (shouldSkipInSummary(line.processedLine, printDecks)) {
                    skipped++
                    continue
                }

                val count = line.decks.size
                val countString = countFormatter.format(count)

                // corrections["bosskinbus"] = "bosskinhoundstooth"
                println("corrections[\"${line.processedLine}\"] = \"TODO$countString\"")

                if (printDecks) {
                    line.decks.take(printDeckSize).forEach {
                        println("  ${it.debugSource}")
                    }
                    line.lines.take(printDeckSize).forEach {
                        println("  $it")
                    }
                }

                currentNum++

                if (currentNum > numToPrint) {
                    break
                }
            }

            println("---------------------------------------")
            println("Skipped: $skipped")
            println("---------------------------------------")

            val extraSummary = false
            if (extraSummary) {
                val countLeftMap = HashMap<Int, Int>()
                for (line in list) {
                    val numDecks = line.decks.size

                    if (numDecks > 14) {
                        println("bigger than 14: ${line.processedLine}")
                    }

                    val count = countLeftMap.getOrDefault(numDecks, 0)
                    countLeftMap[numDecks] = count + 1
                }

                println("Counts Left")
                for (key in countLeftMap.keys.sorted()) {
                    println("[$key]=${countLeftMap[key]}")
                }
            }
        }
    }
}