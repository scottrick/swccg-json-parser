package com.hatfat.swccg.json.parse.count

import com.hatfat.swccg.json.parse.data.deck.SWCCGDeck
import java.text.DecimalFormat

class UnmatchedRegistry {
    val registry = HashMap<String, UnmatchedLine>()

    fun registerUnmatchedLine(line: String, processedLine: String, deck: SWCCGDeck) {
        registry.getOrPut(processedLine) {
            UnmatchedLine(processedLine, HashSet(), HashSet())
        }.also {
            it.decks.add(deck)
            it.lines.add(line)
        }
    }

    fun printSummary(verbose: Boolean = false) {
        println("---------------------------------------")
        println("UnmatchedRegistry Summary")
        println("Total entries: ${registry.keys.size}")
        println("---------------------------------------")

        if (verbose) {
            val countFormatter = DecimalFormat("000")
            val printDecks = true

            val numToPrint = 15
            var currentNum = 0
            val list = registry.values.sortedByDescending { it.decks.size }

            for (line in list) {
                val count = line.decks.size
                val countString = countFormatter.format(count)

                // corrections["bosskinbus"] = "bosskinhoundstooth"
                println("corrections[\"${line.processedLine}\"] = \"TODO$countString\"")

                if (printDecks) {
                    line.decks.take(2).forEach {
                        println("  ${it.debugSource}")
                    }
                    line.lines.take(2).forEach {
                        println("  $it")
                    }
                }

                currentNum++

                if (currentNum > numToPrint) {
                    break
                }
            }
        }
    }
}